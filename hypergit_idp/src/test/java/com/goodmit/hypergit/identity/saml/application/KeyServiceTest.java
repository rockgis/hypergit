package com.goodmit.hypergit.identity.saml.application;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.opensaml.xml.security.SecurityException;
import org.opensaml.xml.security.credential.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;



import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@ActiveProfiles(value = "dev")
class KeyServiceTest {
    @Autowired
    private KeyService keyService;

    @Test
    public void credentionTest() throws SecurityException {
        Credential credential = keyService.resolveCredential();
        log.info(credential.getPublicKey().toString());
    }

    @Test
    public void dateTimeTest() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTime dateTime = DateTime.now();

        log.info("{}\t{}\t{}",localDateTime,dateTime.toDateTime(),dateTime.toLocalDateTime().toDateTime().toString());
    }
}