package com.goodmit.hypergit.global.util.security;

import com.goodmit.hypergit.identity.saml.key.KeyStoreLocator;
import com.goodmit.hypergit.identity.saml.key.KeyStoreType;
import com.goodmit.hypergit.identity.saml.config.SamlProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles="local")
@Slf4j
class KeyStoreLocatorTest {

    @Autowired
    private SamlProperties samlProperties;


    @Test
    void propertiesTest() {
        log.info("{}",samlProperties.getKey().getPassphrase());

    }

    @Test
    void keyStoreList() throws IOException, CertificateException {

        CertificateFactory fac = KeyStoreLocator.getCertificateFactory();
        InputStream is = new ClassPathResource("./keys/saml.crt").getInputStream();
        X509Certificate cert = (X509Certificate) fac.generateCertificate(is);

        String ttt = Base64.getEncoder().encodeToString(cert.getPublicKey().getEncoded());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("-----BEGIN CERTIFICATE-----\n");
        stringBuffer.append(ttt);
        stringBuffer.append("\n-----END CERTIFICATE-----");
//        fac.generateCertificate(new ByteArrayInputStream(stringBuffer.toString().getBytes()));

        log.info("{}",stringBuffer);
    }


    @Test
    void decodePrivateKey() throws IOException {
//        PEMParser pemParser = new PEMParser(new FileReader("/Users/drshin/work/goodmit/project/keys/saml.pem"));
        PemReader pemReader = new PemReader(new InputStreamReader(new ClassPathResource("./keys/saml.pem").getInputStream()));
        try {
            PemObject pemObject = pemReader.readPemObject();
            byte[] privateKeyByte = IOUtils.toByteArray(new ByteArrayInputStream(pemObject.getContent()));
//            log.info("{}", pemObject.);
        } finally {
            pemReader.close();
        }
    }

    @Test
    void addPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, KeyStoreException, UnrecoverableKeyException {
        String entityId = "test";
        String idpPassphrase = "test";
        KeyStore keyStore = KeyStoreLocator.createKeyStore(samlProperties.getKey().getPath(),"test", KeyStoreType.PKCS12);
        log.info(keyStore.getKey("hypergit","test".toCharArray()).getFormat());

        log.info(keyStore.getCertificate("hypergit").getType());
        log.info("{}",KeyStoreType.fromString("11"));
        log.info(keyStore.getProvider().getName());

        KeyStoreLocator.addPrivateKey(keyStore,"hypergit","test");
        JKSKeyManager manager = new JKSKeyManager(keyStore, Collections.singletonMap("hypergit","test"),"hypergit");
        log.info("{}",keyStore.getCertificate("hypergit").equals(manager.getCertificate("hypergit")));
    }

    @Test
    void test( ) throws CertificateException {
        String str = "MIIBfzCCASmgAwIBAgIQWFSKzCWO2ptOAc2F3MKZSzANBgkqhkiG9w0BAQQFADAa\n"
                + "MRgwFgYDVQQDEw9Sb290Q2VydGlmaWNhdGUwHhcNMDExMDE5MTMwNzQxWhcNMzkx\n"
                + "MjMxMjM1OTU5WjAaMRgwFgYDVQQDEw9Vc2VyQ2VydGlmaWNhdGUwXDANBgkqhkiG\n"
                + "9w0BAQEFAANLADBIAkEA24gypa2YFGZHKznEWWbqIWNVXCM35W7RwJwhGpNsuBCj\n"
                + "NT6KEo66F+OOMgZmb0KrEZHBJASJ3n4Cqbt4aHm/2wIDAQABo0swSTBHBgNVHQEE\n"
                + "QDA+gBBch+eYzOPgVRbMq5vGpVWooRgwFjEUMBIGA1UEAxMLUm9vdCBBZ2VuY3mC\n"
                + "EMlg/HS1KKqSRcg8a30Za7EwDQYJKoZIhvcNAQEEBQADQQCYBIHBqQQJePi5Hzfo\n"
                + "CxeUaYlXmvbxVNkxM65Pplsj3h4ntfZaynmlhahH3YsnnA8wk6xPt04LjSId12RB\n"
                + "PeuO";

//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("-----BEGIN CERTIFICATE-----\n");
//        stringBuffer.append(str);
//        stringBuffer.append("\n-----END CERTIFICATE-----");
        str = "-----BEGIN CERTIFICATE-----\n"+str+"\n-----END CERTIFICATE-----";
//        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        Certificate cert = KeyStoreLocator.getCertificateFactory().generateCertificate(new ByteArrayInputStream(str.getBytes()));

        log.info("{}",cert.getPublicKey().getAlgorithm());
    }

}