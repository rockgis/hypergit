package com.goodmit.hypergit;

import com.goodmit.hypergit.identity.saml.application.SAMLService;
import com.goodmit.hypergit.identity.saml.config.SamlProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@ActiveProfiles(profiles="local")
@RunWith(SpringRunner.class)
class HypergitIdpApplicationTests {

    @Autowired SamlProperties samlProperties;

    @Autowired
    SAMLService samlService;


    @Test
    void contextLoads() {
    }

    @Test
    void testSamlPorperties() {

        log.info(String.valueOf(samlProperties.getSloBindings().size()));
        samlProperties.getSsoBindings().forEach( t-> log.info("{}",t.getBindingUri())
        );
    }

    @Test
    void metadataTest() {
        String metadata = samlService.getIDPMetadata();
        log.info(metadata);
    }
    @Test
    void testSamlProperties() {
        log.info("{}",samlProperties.getKey().getPassphrase());
    }
}
