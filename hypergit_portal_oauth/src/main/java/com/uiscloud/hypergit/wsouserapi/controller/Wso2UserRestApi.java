package com.uiscloud.hypergit.wsouserapi.controller;

import com.uiscloud.hypergit.wsouserapi.domain.response.CommonResponse;
import com.uiscloud.hypergit.wsouserapi.domain.response.ErrorResponse;
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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import com.uiscloud.hypergit.wsouserapi.service.Wso2UserJson;

@Tag(name = "Wso2User Check ", description = "Wso2User Check API")
@RestController
@RequestMapping("/api/Wso2User")
@RequiredArgsConstructor
public class Wso2UserRestApi {

    private Wso2UserJson wso2UserJson = new Wso2UserJson();

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
            @Parameter(name = "appUrl", description = "Wso2 Server URL ", example = "https://wso2.uiscloud.com:9445/scim2/Users")
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

        String result = Objects.requireNonNull(responseEntity.getBody()).toString();

        return  ResponseEntity.ok().body(result);
    }

    @Parameters({
            @Parameter(name = "appUrl", description = "Wso2 Server URL ", example = "https://wso2.uiscloud.com:9445/scim2/Users/"),
            @Parameter(name = "userId", description = "User ID ", example = "admin"),
            @Parameter(name = "authHeader", description = "authHeader ", example = "Basic YWRtaW46YWRtaW4=")
    })
    @ResponseBody
    @GetMapping( "{userId}")
    public ResponseEntity<String> getSelectPosts(
            @PathVariable(value = "userId") String userId,
            @RequestParam(value = "appUrl") String appUrl,
            @RequestParam(value = "authHeader") String authHeader
    ) {

        HttpHeaders headers = new HttpHeaders() {{
            String auth = "admin:admin";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII) );

            //String authHeader = "Basic " + new String( encodedAuth );

            set( "Authorization", authHeader );
        }};

      //https://wso2.uiscloud.com:9445/scim2/Users?filter=username Eq admin&attributes=id
        String filter = "?&attributes=id&filter=username Eq " + userId;

        ResponseEntity<String> responseEntity
                = restTemplate.exchange(appUrl+filter,
                HttpMethod.GET, new HttpEntity<>(headers), String.class);

        String result = Objects.requireNonNull(responseEntity.getBody()).toString();

        return  ResponseEntity.ok().body(result);
    }

    @Parameters({
            @Parameter(name = "appUrl", description = "Wso2 Server URL ", example = "https://wso2.uiscloud.com:9445/scim2/Users/6f9dded3-62b6-48dc-884e-ff65429fc6da"),
            @Parameter(name = "rgEN", description = "User rgEN ", example = "radmin"),
            @Parameter(name = "authHeader", description = "authHeader ", example = "Basic YWRtaW46YWRtaW4=")

    })
    @ResponseBody
    @GetMapping( "usersaml/{rgEN}")
    public ResponseEntity<String> setUserPosts(
            @PathVariable(value = "rgEN") String rgEN,
            @RequestParam(value = "appUrl") String appUrl,
            @RequestParam(value = "authHeader") String authHeader
    ) {

        HttpHeaders headers = new HttpHeaders() {{
            String auth = "admin:admin";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII) );

            //String authHeader = "Basic " + new String( encodedAuth );

            set( "Authorization", authHeader );
        }};
        headers.setContentType(MediaType.APPLICATION_JSON);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        SimpleDateFormat sdf = new SimpleDateFormat ("yyyyMMddhh:mm:ss");

        long epoch = System.currentTimeMillis()/1000;

        JSONObject requestJson = wso2UserJson.Wso2UserSaml(rgEN, String.valueOf(epoch));

        System.out.println(requestJson.toString());

        HttpEntity<String> entity = new HttpEntity<String>(requestJson.toString(), headers);

        ResponseEntity<String> responseEntity
                = restTemplate.exchange(appUrl,
                HttpMethod.PATCH, entity, String.class);

        String result = Objects.requireNonNull(responseEntity.getBody()).toString();

        return  ResponseEntity.ok().body(result);
    }

    @Parameters({
            @Parameter(name = "appUrl", description = "Wso2 Server URL ", example = "https://wso2.uiscloud.com:9445/scim2/Users"),
            @Parameter(name = "userId", description = "User ID ", example = "admin"),
            @Parameter(name = "userEmail", description = "User Email ", example = "lhlee@hcs.com"),
            @Parameter(name = "givenName", description = "User givenName ", example = "givenName"),
            @Parameter(name = "familyName", description = "User familyName ", example = "familyName"),
            @Parameter(name = "authHeader", description = "authHeader ", example = "Basic YWRtaW46YWRtaW4=")

    })
    @ResponseBody
    @GetMapping( "useradd")
    public ResponseEntity<String> setUserPosts(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "userEmail") String userEmail,
            @RequestParam(value = "givenName") String givenName,
            @RequestParam(value = "familyName") String familyName,
            @RequestParam(value = "appUrl") String appUrl,
            @RequestParam(value = "authHeader") String authHeader
    ) {

        HttpHeaders headers = new HttpHeaders() {{
            String auth = "admin:admin";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII) );

            //String authHeader = "Basic " + new String( encodedAuth );

            set( "Authorization", authHeader );
        }};
        headers.setContentType(MediaType.APPLICATION_JSON);


        JSONObject requestJson = wso2UserJson.Wso2UserAdd(userId, "Goodmit0802!", userEmail, givenName, familyName);

        HttpEntity<String> entity = new HttpEntity<String>(requestJson.toString(), headers);

        ResponseEntity<String> responseEntity
                = restTemplate.exchange(appUrl,
                HttpMethod.POST, entity, String.class);

        String result = Objects.requireNonNull(responseEntity.getBody()).toString();

        return  ResponseEntity.ok().body(result);
    }


    @Parameters({
            @Parameter(name = "appUrl", description = "Wso2 Server URL ", example = "https://wso2.uiscloud.com:9445/scim2/Users/"),
            @Parameter(name = "userId", description = "User ID ", example = "admin"),
            @Parameter(name = "authHeader", description = "authHeader ", example = "Basic YWRtaW46YWRtaW4=")
    })
    @GetMapping( "/del/{userId}")
    public ResponseEntity<String> userDelete(
            @RequestParam(value = "appUrl") String appUrl,
            @PathVariable(value = "userId") String userId,
            @RequestParam(value = "authHeader") String authHeader
    ) {

        HttpHeaders headers = new HttpHeaders() {{
            String auth = "admin:admin";
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(StandardCharsets.US_ASCII) );

            //String authHeader = "Basic " + new String( encodedAuth );

            set( "Authorization", authHeader );
        }};
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println(appUrl);
        System.out.println(userId);

        String requestJson = "{\n" +
                "  \"schemas\": [\n" +
                "    \"urn:ietf:params:scim:api:messages:2.0:PatchOp\"\n" +
                "  ],\n" +
                "  \"Operations\": [\n" +
                "{ \"op\": \"remove\", \"path\": \"phoneNumbers\" }\n" +
                "  ]\n" +
                "}";

        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);

        ResponseEntity<String> responseEntity
                = restTemplate.exchange(appUrl+userId,
                HttpMethod.DELETE, entity, String.class);

        String result = Objects.requireNonNull(responseEntity.getBody()).toString();

        return  ResponseEntity.ok().body(result);
    }



}
