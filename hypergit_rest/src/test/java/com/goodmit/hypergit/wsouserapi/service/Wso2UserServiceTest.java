package com.goodmit.hypergit.wsouserapi.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;



class Wso2UserServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    @DisplayName("should ignore ssl certification validation error")
    void getWso2User() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        // 모든 인증서를 신뢰하도록 설정한다
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, (X509Certificate[] chain, String authType) -> true).build();

        httpClientBuilder.setSSLContext(sslContext);

        // Https 인증 요청시 호스트네임 유효성 검사를 진행하지 않게 한다.
        SSLConnectionSocketFactory sslSocketFactory
                = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        Registry<ConnectionSocketFactory> socketFactoryRegistry
                = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslSocketFactory).build();

        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        httpClientBuilder.setConnectionManager(connMgr);

        // RestTemplate 와 HttpClient 연결
        HttpClient httpClient = httpClientBuilder.build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        // API 기본 인증 헤드 생성
        HttpHeaders headers = new HttpHeaders() {{
            String auth = "admin:admin";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII) );

            //String authHeader = "Basic " + new String( encodedAuth );

            String  authHeader = "Basic YWRtaW46YWRtaW4=";

            set( "Authorization", authHeader );
        }};

        ResponseEntity<String> responseEntity
                = restTemplate.exchange("https://wso2.uiscloud.com:9445/scim2/Users/",
                HttpMethod.GET, new HttpEntity<>(headers), String.class);

        System.out.println(responseEntity.getBody().toString());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }


}
