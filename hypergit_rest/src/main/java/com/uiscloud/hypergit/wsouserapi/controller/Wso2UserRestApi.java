package com.goodmit.hypergit.wsouserapi.controller;

import com.goodmit.hypergit.restapi.domain.response.ErrorResponse;
import com.goodmit.hypergit.restapi.domain.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Tag(name = "Wso2User Check ", description = "Wso2User Check API")
@RestController
@RequestMapping("/api/Wso2User")
@RequiredArgsConstructor
public class Wso2UserRestApi {

    @Autowired
    private RestTemplate restTemplate;

    @Operation(summary = "Wso2 Server User\n" +
            "를 확인 하기 위해 사용 하는 API.", description = "Wso2User 의 리스트을 가져오기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CommonResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST" , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND" , content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))

})
    @Parameters({
            @Parameter(name = "appUrl", description = "Wso2 Server URL ", example = "https://wso2.uiscloud.com:9445/scim2/Users/"),
           })
    @ResponseBody
    @GetMapping( "")
    public ResponseEntity<String> getPosts(
            @RequestParam(value = "appUrl") String appUrl

    ) {

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
                = restTemplate.exchange(appUrl,
                HttpMethod.GET, new HttpEntity<>(headers), String.class);

        String result = responseEntity.getBody().toString();

        return  ResponseEntity.ok().body(result);
    }

    @ResponseBody
    @GetMapping( "{appId}")
    public ResponseEntity<String> getSelectPosts(
            @RequestParam(value = "appId") String appId,
            @RequestParam(value = "transactionId") String transactionId,
            @RequestParam(value = "data") String data
    ) {

        String result = "3,001,6,6,6,6,,002,0,0,0,0,,003,0,0,0,0";

        return  ResponseEntity.ok().body(result);
    }
}
