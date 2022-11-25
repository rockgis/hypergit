package com.goodmit.hypergit.identity.saml.metadata;

import com.goodmit.hypergit.identity.saml.auth.SamlBuilder;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.common.Extensions;
import org.opensaml.saml2.metadata.*;
import org.opensaml.util.URLBuilder;
import org.opensaml.xml.security.credential.UsageType;
import org.opensaml.xml.util.Pair;
import org.springframework.security.saml.SAMLLogoutProcessingFilter;
import org.springframework.security.saml.SAMLProcessingFilter;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class IdpMetadataGenerator extends MetadataGenerator {

    private final String ssoURL;
    private final String sloURL;

    @Builder
    protected IdpMetadataGenerator(String ssoURL, String sloURL) {
        this.ssoURL = ssoURL;
        this.sloURL = sloURL;
    }

    @Override
    public EntityDescriptor generateMetadata() {
        Collection<String> includedNameID = getNameID();

        String entityId = getEntityId();
        String entityBaseURL = getEntityBaseURL();
        String entityAlias = getEntityAlias();

        validateRequiredAttributes(entityId, entityBaseURL);

        EntityDescriptor descriptor = SamlBuilder.buildSAMLObject(EntityDescriptor.class,EntityDescriptor.DEFAULT_ELEMENT_NAME);
        descriptor.setEntityID(entityId);

        IDPSSODescriptor ssoDescriptor = buildIDPSSODescriptor(entityBaseURL, entityAlias, isRequestSigned(), includedNameID);
        if (ssoDescriptor != null) {
            descriptor.getRoleDescriptors().add(ssoDescriptor);
        }

        return descriptor;
    }

    protected IDPSSODescriptor buildIDPSSODescriptor(String entityBaseURL, String entityAlias, boolean requestSigned, Collection<String> includedNameID) {
        IDPSSODescriptor idpssoDescriptor =
                SamlBuilder.buildSAMLObject(IDPSSODescriptor.class,IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
        idpssoDescriptor.setWantAuthnRequestsSigned(requestSigned);
        idpssoDescriptor.addSupportedProtocol(SAMLConstants.SAML20P_NS);

        idpssoDescriptor.getNameIDFormats().addAll(getNameIDFormat(includedNameID));

        addSingleSignOnService(idpssoDescriptor,mapAliases(getBindingsSSO()));
        addSingleLogoutService(idpssoDescriptor, mapAliases(getBindingsSLO()));
        // Build extensions
        Extensions extensions = buildExtensions(entityBaseURL, entityAlias);
        if (extensions != null) {
            idpssoDescriptor.setExtensions(extensions);
        }

        // Populate key aliases
        String signingKey = getSigningKey();
        String encryptionKey = getEncryptionKey();
        String tlsKey = getTLSKey();

        // Generate key info
        log.info("generate idp metadata with signing key : {}",!Objects.isNull(signingKey));
        if (!Objects.isNull(signingKey)) {
            idpssoDescriptor.getKeyDescriptors()
                    .add(getKeyDescriptor(UsageType.SIGNING, getServerKeyInfo(signingKey)));
        }

        log.info("generate idp metadata with encryption key : {}",!Objects.isNull(encryptionKey));
        if (!Objects.isNull(encryptionKey)) {
            idpssoDescriptor.getKeyDescriptors()
                    .add(getKeyDescriptor(UsageType.ENCRYPTION, getServerKeyInfo(encryptionKey)));
        }

        if (!Objects.isNull(tlsKey) && !(tlsKey.equals(encryptionKey)) && !(tlsKey.equals(signingKey))) {
            idpssoDescriptor.getKeyDescriptors()
                    .add(getKeyDescriptor(UsageType.UNSPECIFIED, getServerKeyInfo(tlsKey)));
        }

        return idpssoDescriptor;
    }

    @Override
    public ExtendedMetadata generateExtendedMetadata() {
        if(!Objects.isNull(getExtendedMetadata())){
            getExtendedMetadata().setIdpDiscoveryEnabled(false);
        }
        return super.generateExtendedMetadata();
    }

    protected void addSingleLogoutService(IDPSSODescriptor idpssoDescriptor, Collection<String> bindingsSLO) {
        String entityBaseURL = getEntityBaseURL();
        String entityAlias = getEntityAlias();

        BindingType.getAllBindingUriList().stream()
                .filter(bindingsSLO::contains)
                .forEach(bindingUri->
                        idpssoDescriptor.getSingleLogoutServices().add(
                                getSingleLogoutService(entityBaseURL,entityAlias,bindingUri)
                        ));
    }

    protected void addSingleSignOnService(IDPSSODescriptor idpssoDescriptor, Collection<String> bindingsSSO) {
        String entityBaseURL = getEntityBaseURL();
        String entityAlias = getEntityAlias();

        BindingType.getAllBindingUriList().stream()
                .filter(bindingsSSO::contains)
                .forEach(bindingUri->
                        idpssoDescriptor.getSingleSignOnServices().add(
                                getSingleSignOnService(entityBaseURL,entityAlias,bindingUri)
                        ));
    }

    protected ArtifactResolutionService getArtifactResolutionService(String entityBaseURL, String entityAlias, boolean isDefault, int index, String filterURL, String binding) {
        ArtifactResolutionService consumer = SamlBuilder.buildSAMLObject(ArtifactResolutionService.class,ArtifactResolutionService.DEFAULT_ELEMENT_NAME);
        consumer.setLocation(getServerURL(entityBaseURL, entityAlias, filterURL));
        consumer.setBinding(binding);
        consumer.setIndex(index);
        consumer.setIsDefault(isDefault);
        return consumer;
    }

    protected SingleSignOnService getSingleSignOnService(String entityBaseURL, String entityAlias, String binding) {
        SingleSignOnService consumer =
                SamlBuilder.buildSAMLObject(SingleSignOnService.class,SingleSignOnService.DEFAULT_ELEMENT_NAME);
        consumer.setBinding(binding);
        consumer.setLocation(getServerURL(entityBaseURL,entityAlias, getSSOFilterPath()));
        return consumer;
    }

    protected SingleLogoutService getSingleLogoutService(String entityBaseURL, String entityAlias, String binding) {
        SingleLogoutService consumer =
                SamlBuilder.buildSAMLObject(SingleLogoutService.class,SingleLogoutService.DEFAULT_ELEMENT_NAME);
        consumer.setBinding(binding);
        consumer.setLocation(getServerURL(entityBaseURL,entityAlias,getSAMLLogoutFilterPath()));
        return consumer;
    }

    private String getServerURL(String entityBaseURL, String entityAlias, String processingURL) {
        return getServerURL(entityBaseURL, entityAlias, processingURL, null);
    }

    private String getServerURL(String entityBaseURL, String entityAlias, String processingURL, Map<String, String> parameters) {
        StringBuilder result = new StringBuilder();
        result.append(entityBaseURL);
        if (!processingURL.startsWith("/")) {
            result.append("/");
        }
        result.append(processingURL);

        if (StringUtils.hasText(entityAlias)) {
            if (!processingURL.endsWith("/")) {
                result.append("/");
            }
            result.append("alias/");
            result.append(entityAlias);
        }

        String resultString = result.toString();
        if (parameters == null || parameters.size() == 0) {
            return resultString;
        } else {
            // Add parameters
            URLBuilder returnUrlBuilder = new URLBuilder(resultString);
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                returnUrlBuilder.getQueryParams().add(new Pair<>(entry.getKey(), entry.getValue()));
            }
            return returnUrlBuilder.buildURL();
        }
    }

    private String getSAMLLogoutFilterPath() {
        return StringUtils.hasText(sloURL)?sloURL:SAMLLogoutProcessingFilter.FILTER_URL;
    }

    private String getSSOFilterPath() {
        return StringUtils.hasText(ssoURL)?ssoURL: SAMLProcessingFilter.FILTER_URL;
    }
}
