package com.goodmit.hypergit.identity.saml.application.impl;

import com.goodmit.hypergit.identity.saml.application.SAMLService;
import com.goodmit.hypergit.identity.saml.config.properties.SamlProperties;
import com.goodmit.hypergit.identity.saml.application.KeyService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.io.MarshallingException;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.security.saml.metadata.MetadataMemoryProvider;
import org.springframework.security.saml.util.SAMLUtil;

import java.util.Arrays;

@Slf4j
public class SAMLServiceImpl implements SAMLService {

    private final MetadataGenerator metadataGenerator;
    private final KeyService keyService;

    @Builder
    protected SAMLServiceImpl(SamlProperties samlProperties, MetadataGenerator metadataGenerator, KeyService keyService) {
        this.metadataGenerator = metadataGenerator;
        this.keyService = keyService;
    }

    @Override
    public String getIDPMetadata() {
        EntityDescriptor generatedDescriptor = metadataGenerator.generateMetadata();
        try {
            MetadataManager metadataManager = getMetadataMgr(generatedDescriptor);
            return SAMLUtil.getMetadataAsString(metadataManager, keyService.getKeyManager(), generatedDescriptor, null);
        } catch (MarshallingException | MetadataProviderException e) {
            throw new RuntimeException(e);
        }
    }

    private MetadataManager getMetadataMgr(EntityDescriptor generatedDescriptor) throws MetadataProviderException {
        MetadataMemoryProvider memoryProvider = new MetadataMemoryProvider(generatedDescriptor);
        memoryProvider.initialize();

        MetadataManager metadataManager = new CachingMetadataManager(Arrays.asList(memoryProvider));
        metadataManager.setKeyManager(keyService.getKeyManager());

        metadataManager.setHostedSPName(generatedDescriptor.getEntityID());
        metadataManager.refreshMetadata();
        return metadataManager;
    }

}
