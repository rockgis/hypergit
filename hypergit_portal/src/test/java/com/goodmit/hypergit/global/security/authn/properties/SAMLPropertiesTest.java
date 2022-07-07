package com.goodmit.hypergit.global.security.authn.properties;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.StaticResourceLocation;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles="dev")
@Slf4j
class SAMLPropertiesTest {

    @Autowired
    SAMLProperties samlProperties;

    @Test
    public void idpUrlTest() {
        log.info("idpUrl : {}",samlProperties.getIpdUrl());
        assertNotNull(samlProperties.getIpdUrl());
    }

    @Test
    public void acsUrlTest() {
        log.info("acsUrl : {}",samlProperties.getAcsUrl());
        assertNotNull(samlProperties.getAcsUrl());

        List<String> tt = Arrays.stream(StaticResourceLocation.values())
                .flatMap(StaticResourceLocation::getPatterns)
                .collect(Collectors.toList());

        tt.forEach(str->log.info("---->{}",str));
    }
}