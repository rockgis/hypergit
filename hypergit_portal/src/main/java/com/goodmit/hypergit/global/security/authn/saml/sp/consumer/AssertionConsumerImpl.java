package com.goodmit.hypergit.global.security.authn.saml.sp.consumer;

import com.goodmit.hypergit.global.security.authn.saml.util.SamlUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.opensaml.saml2.core.*;
import org.opensaml.xml.security.credential.BasicCredential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureValidator;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.validation.ValidationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.List;

@Slf4j
public class AssertionConsumerImpl implements AssertionConsumer {

    private final int assertionValidTime;

    @Builder
    protected AssertionConsumerImpl(@NonNull int assertionValidTime) {
        this.assertionValidTime = assertionValidTime;
    }

    @Override
    public UserDetails consume(Response samlResponse) throws AuthenticationException {
        validateSignature(samlResponse);
        checkAuthnInstant(samlResponse);
        Assertion assertion = samlResponse.getAssertions().get(0);
        log.debug("Assertion[{}]", SamlUtil.samlObjectToString(assertion));
        return createUser(assertion);
    }

    private UserDetails createUser(Assertion assertion) {
        String nameID = assertion.getSubject().getNameID().getValue();
        AttributeStatement attributeStatement = assertion.getAttributeStatements().get(0);
        List<Attribute> attributes = attributeStatement.getAttributes();
        //TODO : fix authorities



        User user = new User(nameID,null,null);
        return user;
    }

    private void validateSignature(Response samlResponse) throws AuthenticationException {
        try {
            Signature signature = samlResponse.getSignature();
            PublicKey publicKey = extractPublicKey(signature);
            SignatureValidator validator = createValidator(publicKey);
            validator.validate(signature);
            log.debug("Signature validation success");
        } catch (CertificateException e) {
            log.error("Invalid certification(public key)", e);
            throw new BadCredentialsException("Invalid certification(public key)", e);
        } catch (ValidationException e) {
            log.error("Signature validation fail.", e);
            throw new BadCredentialsException("Signature validation fail", e);
        }
    }

    private PublicKey extractPublicKey(Signature signature) throws CertificateException {
        X509Data x509Data = signature.getKeyInfo().getX509Datas().get(0);
        X509Certificate cert = x509Data.getX509Certificates().get(0);
        String wrappedCert = wrapBase64String(cert.getValue());
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certFactory.generateCertificate(new ByteArrayInputStream(wrappedCert.getBytes()));
        return certificate.getPublicKey();
    }

    private String wrapBase64String(String base64String) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("-----BEGIN CERTIFICATE-----\n");
        stringBuffer.append(String.valueOf(base64String));
        stringBuffer.append("\n-----END CERTIFICATE-----");
        return  stringBuffer.toString();
    }

    private SignatureValidator createValidator(PublicKey publicKey) {
        BasicCredential credential = new BasicCredential();
        credential.setPublicKey(publicKey);
        return new SignatureValidator(credential);
    }

    private void checkAuthnInstant(Response samlResponse) throws AuthenticationException {
        Assertion assertion = samlResponse.getAssertions().get(0);
        AuthnStatement authnStatement = assertion.getAuthnStatements().get(0);
        DateTime authnInstant = authnStatement.getAuthnInstant();
        log.debug("AuthnInstant[{}]", authnInstant);

        DateTime validTime = authnInstant.plusMinutes(assertionValidTime);
        if (DateTime.now().compareTo(validTime) > 0) {
            throw new CredentialsExpiredException("AuthnInstant time out : " + authnInstant);
        }
    }
}
