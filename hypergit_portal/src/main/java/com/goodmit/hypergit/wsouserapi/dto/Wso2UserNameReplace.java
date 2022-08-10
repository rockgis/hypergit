package com.goodmit.hypergit.wsouserapi.dto;

import org.json.JSONObject;

public class Wso2UserNameReplace {

    public JSONObject Wso2UserNameReplace(String givenName , String familyName){

        JSONObject replacseName = new JSONObject();
        replacseName.put("givenName", givenName);
        replacseName.put("familyName", familyName);

        JSONObject name = new JSONObject();
        name.put("name", replacseName);

        return name;
    }


}
