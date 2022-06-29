package com.goodmit.hypergit.identity.saml.auth;

import com.goodmit.hypergit.identity.saml.auth.dao.SamlAttribute;
import com.goodmit.hypergit.identity.saml.auth.dao.SamlPrincipal;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.saml2.core.*;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.schema.XSAny;
import org.opensaml.xml.schema.impl.XSAnyBuilder;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.signature.*;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class SamlBuilder {
    private static final XMLObjectBuilderFactory builderFactory;

    static{
        builderFactory = Configuration.getBuilderFactory();
    }

    public static <T> T buildSAMLObject(@NonNull final Class<T> objectClass, QName qName) {
        return objectClass.cast(builderFactory.getBuilder(qName).buildObject(qName));
    }

    public static Issuer buildIssuer(String issueEntityName) {
        Issuer issuer = buildSAMLObject(Issuer.class,Issuer.DEFAULT_ELEMENT_NAME);
        issuer.setValue(issueEntityName);
        issuer.setFormat(NameIDType.ENTITY);
        return issuer;
    }

    public static Status buildStatus(String value) {
        Status status = buildSAMLObject(Status.class, Status.DEFAULT_ELEMENT_NAME);
        StatusCode statusCode = buildSAMLObject(StatusCode.class, StatusCode.DEFAULT_ELEMENT_NAME);
        statusCode.setValue(value);
        status.setStatusCode(statusCode);
        return status;
    }

    public static Assertion buildAssertion(SamlPrincipal principal, Status status, String entityId, String keyAlias) {
        DateTime time = DateTime.now();
        Assertion assertion = buildSAMLObject(Assertion.class,Assertion.DEFAULT_ELEMENT_NAME);

        if(status.getStatusCode().getValue().equals(StatusCode.SUCCESS_URI)){
            Subject subject = buildSubject(principal.getNameID(),principal.getNameIDType(),
                    principal.getAssertionConsumerServiceUrl(),principal.getRequestID(),time);
            assertion.setSubject(subject);
        }

        Audience audience = buildSAMLObject(Audience.class,Audience.DEFAULT_ELEMENT_NAME);
        audience.setAudienceURI(principal.getServiceProviderEntityID());

        AudienceRestriction audienceRestriction = buildSAMLObject(AudienceRestriction.class,AudienceRestriction.DEFAULT_ELEMENT_NAME);
        audienceRestriction.getAudiences().add(audience);

        Conditions conditions = buildSAMLObject(Conditions.class,Conditions.DEFAULT_ELEMENT_NAME);
        conditions.setNotBefore(time.minusMinutes(3));
        conditions.setNotOnOrAfter(time.plusMinutes(3));
        conditions.getAudienceRestrictions().add(audienceRestriction);
        assertion.setConditions(conditions);

        Issuer issuer = buildIssuer(entityId);
        assertion.setIssuer(issuer);

        AuthnStatement authnStatement = buildAuthnStatement(time, keyAlias);
        assertion.getAuthnStatements().add(authnStatement);

        assertion.getAttributeStatements().add(buildAttributeStatement(principal.getAttributes(),principal.getNameIDType()));

        assertion.setID(randomSAMLId());
        assertion.setIssueInstant(time);
        return assertion;
    }


    private static AttributeStatement buildAttributeStatement(List<SamlAttribute> attributes, String nameIdType) {
        AttributeStatement attributeStatement = buildSAMLObject(AttributeStatement.class,AttributeStatement.DEFAULT_ELEMENT_NAME);
        attributes.forEach(entry -> attributeStatement.getAttributes().add(buildAttribute(entry.getName(), entry.getValues(), nameIdType)));
        return attributeStatement;
    }

    private static Attribute buildAttribute(String name, List<String> values,String nameIdType) {
        XSAnyBuilder anyBuilder = (XSAnyBuilder) Configuration.getBuilderFactory().getBuilder(XSAny.TYPE_NAME);

        Attribute attribute = buildSAMLObject(Attribute.class,Attribute.DEFAULT_ELEMENT_NAME);
        attribute.setName(name);
        attribute.setNameFormat(nameIdType);

        List<XSAny> xsStringList = values.stream().map(value ->{
            XSAny anyValue = anyBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME,XSAny.TYPE_NAME);
            anyValue.setTextContent(value);
            return anyValue;
        }).collect(Collectors.toList());

        attribute.getAttributeValues().addAll(xsStringList);
        return attribute;
    }

    private static AuthnStatement buildAuthnStatement(DateTime authInstant, String entityId) {

        AuthnStatement authnStatement = buildSAMLObject(AuthnStatement.class,AuthnStatement.DEFAULT_ELEMENT_NAME);

        AuthnContextClassRef authnContextClassRef = buildSAMLObject(AuthnContextClassRef.class,AuthnContextClassRef.DEFAULT_ELEMENT_NAME);
        authnContextClassRef.setAuthnContextClassRef(AuthnContext.PASSWORD_AUTHN_CTX);

        AuthenticatingAuthority authenticatingAuthority = buildSAMLObject(AuthenticatingAuthority.class,AuthenticatingAuthority.DEFAULT_ELEMENT_NAME);
        authenticatingAuthority.setURI(entityId);

        AuthnContext authnContext = buildSAMLObject(AuthnContext.class,AuthnContext.DEFAULT_ELEMENT_NAME);
        authnContext.setAuthnContextClassRef(authnContextClassRef);
        authnContext.getAuthenticatingAuthorities().add(authenticatingAuthority);

        authnStatement.setAuthnContext(authnContext);
        authnStatement.setAuthnInstant(authInstant);
        return authnStatement;
    }

    private static Subject buildSubject(String subjectNameID, String subjectNameIDType, String recipient, String inResponseTo,DateTime time) {
        Subject subject = buildSAMLObject(Subject.class,Subject.DEFAULT_ELEMENT_NAME);

        NameID nameID = buildSAMLObject(NameID.class,NameID.DEFAULT_ELEMENT_NAME);
        nameID.setValue(subjectNameID);
        nameID.setFormat(subjectNameIDType);
        subject.setNameID(nameID);

        SubjectConfirmation subjectConfirmation = buildSAMLObject(SubjectConfirmation.class,SubjectConfirmation.DEFAULT_ELEMENT_NAME);
        subjectConfirmation.setMethod(SubjectConfirmation.METHOD_BEARER);

        SubjectConfirmationData subjectConfirmationData = buildSAMLObject(SubjectConfirmationData.class,SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
        subjectConfirmationData.setRecipient(recipient);
        subjectConfirmationData.setInResponseTo(inResponseTo);
        subjectConfirmationData.setNotOnOrAfter(time.plusMinutes(8*60));
        subjectConfirmation.setSubjectConfirmationData(subjectConfirmationData);

        subject.getSubjectConfirmations().add(subjectConfirmation);
        return subject;
    }

    public static void signAssertion(SignableXMLObject signableXMLObject, Credential signingCredential)
            throws MarshallingException, SignatureException {
        Signature signature = buildSignature(signingCredential);
        signableXMLObject.setSignature(signature);

        Configuration.getMarshallerFactory().getMarshaller(signableXMLObject).marshall(signableXMLObject);
        Signer.signObject(signature);
    }

    public static Signature buildSignature(Credential signingCredential) {
        Signature signature = buildSAMLObject(Signature.class,Signature.DEFAULT_ELEMENT_NAME);

        signature.setSigningCredential(signingCredential);
        signature.setSignatureAlgorithm(
                Configuration.getGlobalSecurityConfiguration().getSignatureAlgorithmURI(signingCredential));
        signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

        return signature;
    }

    public static String randomSAMLId() {
        return  "_".concat(UUID.randomUUID().toString());
    }
}
