package com.goodmit.hypergit;

import com.goodmit.hypergit.identity.saml.application.SAMLService;
import com.goodmit.hypergit.identity.saml.config.properties.SamlProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@ActiveProfiles(profiles="dev")
@RunWith(SpringRunner.class)
class HypergitIdpApplicationTests {

    @Autowired SamlProperties samlProperties;

    @Autowired
    SAMLService samlService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Test
    void contextLoads() {
    }

    @Test
    void testSamlPorperties() {

        Assert.assertNotNull(samlProperties.getSloBindings());
        Assert.assertTrue(samlProperties.getSsoBindings().size() > 0);

    }

    @Test
    void metadataTest() {
        String metadata = samlService.getIDPMetadata();
        Assert.assertFalse(StringUtils.isBlank(metadata));

    }
    @Test
    void testSamlProperties() {
        log.info("{}",samlProperties.getKey().getPassphrase());
    }

    void testttt() {
        UserDetails userDetails;
        SimpleGrantedAuthority simpleGrantedAuthority;
    }

    @Test
    public void testPassword() {
        log.info("{}",passwordEncoder.encode("admin"));
    }

}
