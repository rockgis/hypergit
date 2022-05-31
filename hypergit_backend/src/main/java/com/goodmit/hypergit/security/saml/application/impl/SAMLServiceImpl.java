package com.goodmit.hypergit.security.saml.application.impl;

import com.goodmit.hypergit.security.saml.application.SAMLService;
import com.goodmit.hypergit.security.saml.config.SamlProperties;
import com.goodmit.hypergit.security.saml.metadata.BindingType;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.xml.io.MarshallingException;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.security.saml.metadata.MetadataMemoryProvider;
import org.springframework.security.saml.util.SAMLUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class SAMLServiceImpl implements SAMLService {

    private final MetadataGenerator metadataGenerator;
    private final SamlProperties samlProperties;
    private final JKSKeyManager keyManager;

    @Builder
    protected SAMLServiceImpl(SamlProperties samlProperties, MetadataGenerator metadataGenerator, JKSKeyManager keyManager) {
        this.metadataGenerator = metadataGenerator;
        this.samlProperties = samlProperties;
        this.keyManager = keyManager;
    }

    @Override
    public String getIDPMetadata() {
        metadataGenerator.setEntityId(samlProperties.getEntityId());
        metadataGenerator.setKeyManager(keyManager);
        Collection<String> bindingsSSO = samlProperties.getSsoBindings().stream().map(BindingType::getBindingUri).collect(Collectors.toList());
        Collection<String> bindingsSLO = samlProperties.getSloBindings().stream().map(BindingType::getBindingUri).collect(Collectors.toList());

        metadataGenerator.setBindingsSSO(bindingsSSO);
        metadataGenerator.setBindingsSLO(bindingsSLO);

        metadataGenerator.setNameID(Arrays.asList(samlProperties.getNameIDType()));
        metadataGenerator.setKeyManager(keyManager);

        EntityDescriptor generatedDescriptor = metadataGenerator.generateMetadata();

        try {
            MetadataManager metadataManager = getMetadataMgr(generatedDescriptor);
            return SAMLUtil.getMetadataAsString(metadataManager, keyManager , generatedDescriptor, null);
        } catch (MarshallingException | MetadataProviderException e) {
            throw new RuntimeException(e);
        }
    }

    private MetadataManager getMetadataMgr(EntityDescriptor generatedDescriptor) throws MetadataProviderException {
        MetadataMemoryProvider memoryProvider = new MetadataMemoryProvider(generatedDescriptor);
        memoryProvider.initialize();

        MetadataManager metadataManager = new CachingMetadataManager(Arrays.asList(memoryProvider));
        metadataManager.setKeyManager(keyManager);

        metadataManager.setHostedSPName(generatedDescriptor.getEntityID());
        metadataManager.refreshMetadata();
        return metadataManager;
    }

}
