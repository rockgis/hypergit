package com.uiscloud.hypergit.wsouserapi.service;

import com.uiscloud.hypergit.wsouserapi.dto.Wso2UserNameReplace;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Wso2UserJson {


    public JSONObject Wso2UserSaml(String rgEN , String validTimeStamp){

        List<String> schemas_list = new ArrayList<>();
        schemas_list.add("urn:ietf:params:scim:api:messages:2.0:PatchOp");

        JSONArray schemas_ja = new JSONArray(schemas_list);

        JSONObject jo = new JSONObject();
        jo.put("schemas", schemas_ja);

        List<JSONObject> Operations_list = new ArrayList<>();

        JSONObject rgen = new JSONObject();
        rgen.put("validTimeStamp", validTimeStamp);
        rgen.put("rgEN", rgEN);

        JSONObject value = new JSONObject();
        value.put("urn:ietf:params:scim:schemas:extension:enterprise:2.0:User", rgen);


        JSONObject Operations = new JSONObject();
        Operations.put("op", "replace");
        Operations.put("value", value);

        Operations_list.add(Operations);

        jo.put("Operations", Operations_list);

        return jo;

    }

    public JSONObject Wso2UserAdd(String userName , String password, String emails, String givenName , String familyName){

        List<String> schemas_list = new ArrayList<>();
        schemas_list.add("");

        JSONArray schemas_ja = new JSONArray(schemas_list);

        JSONObject jo = new JSONObject();
        jo.put("schemas", schemas_ja);

        JSONObject replacseName = new JSONObject();
        replacseName.put("givenName", givenName);
        replacseName.put("familyName", familyName);

        jo.put("name", replacseName);
        jo.put("userName", userName);
        jo.put("password", password);


        List<String> emailslist = new ArrayList<>();
        emailslist.add(emails);

        JSONArray emails_Json = new JSONArray(emailslist);

        jo.put("emails", emails_Json);

        return jo;

    }


    // name 변경 하기
    public JSONObject Wso2UserNameReplace(String schemas , String op_status, String givenName , String familyName){

        List<String> schemas_list = new ArrayList<>();
        schemas_list.add(schemas);

        JSONArray schemas_ja = new JSONArray(schemas_list);

        JSONObject jo = new JSONObject();
        jo.put("schemas", schemas_ja);

        Wso2UserNameReplace Wso2UserNameReplace = new Wso2UserNameReplace();

        JSONObject name = Wso2UserNameReplace.Wso2UserNameReplace( givenName , familyName);

        JSONObject op = new JSONObject();
        op.put("op", op_status);
        op.put("value", name);

        List<JSONObject> Operations_list = new ArrayList<>();
        Operations_list.add(op);

        JSONArray Operations_Json = new JSONArray(Operations_list);

        jo.put("Operations", Operations_Json);

        return jo;

    }


}
