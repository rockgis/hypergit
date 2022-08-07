package com.goodmit.hypergit.wsouserapi.service.util;

import com.goodmit.hypergit.wsouserapi.dto.*;
import com.goodmit.hypergit.wsouserapi.service.util.*;

import okhttp3.internal.http.HttpHeaders;
import org.apache.commons.collections.map.MultiValueMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


class Wso2UserJsonTest {

    @Test
    void jsonObject() {

        String schemas = "urn:ietf:params:scim:api:messages:2.0:PatchOp";

        JSONObject op = new JSONObject();

        Wso2UserJson Wso2UserJson = new Wso2UserJson();
        op = Wso2UserJson.Wso2UserNameReplace(schemas,"replace","Jone","Jone");

        System.out.println(op.toString());


    }

    @Test
    void jsonObjectUseradd() {

        /*{
            "schemas": [],
            "name":     {
            "givenName": "lee",
                    "familyName": "lee"  },
            "userName":    "lee",
                "password":   "abclee123",
                "emails":    ["ceo@uiscloud.net"  ]
        }*/

        String schemas = "";

        JSONObject op = new JSONObject();

        Wso2UserJson Wso2UserJson = new Wso2UserJson();
        op = Wso2UserJson.Wso2UserAdd("441606" , "Stringpassword", "ceo@uiscloud.net","Jone","Jone");

        System.out.println(op.toString());


    }




}