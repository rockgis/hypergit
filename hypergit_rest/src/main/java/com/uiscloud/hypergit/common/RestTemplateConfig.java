package com.goodmit.hypergit.common;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collections;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class RestTemplateConfig {


    public RestTemplateConfig() {
        System.out.println("RestTemplateConfig 초기화");
    }

    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        System.out.println("restTemplate bean");


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


		/*
		 사용중 -  서버에서 응답 늦게 줄때 -> Thread 급증 -> 문제 차단( Connection 수 제한)
		 */

       /* HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        HttpClient client = HttpClientBuilder.create()
                .setMaxConnTotal(50)
                .setMaxConnPerRoute(20)
                .build();

        System.err.println("client : " + client);


        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        factory.setHttpClient(client);

        System.err.println("factory : " + factory);

        //RestTemplate restTemplate = new RestTemplate(factory);
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));*/
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));

        System.out.println("객체 : " + restTemplate);

        return restTemplate;

    }

}

//로그 출력
class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor{

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        // 전
        System.out.println("url 호출 전 ");

        ClientHttpResponse response = execution.execute(request, body);
        // 후
        System.out.println("url 호출 후 ");
        return response;
    }




}
