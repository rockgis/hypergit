package com.goodmit.hypergit.wsouserapi.domain.values;

public class WsoUser {

    private String userName;
    private String emails;
    private String password;

    private String id;

    private String givenName;

    private String familyName;



    public WsoUser(String id, String userName, String emails, String password ,String givenName , String familyName) {
            this.id = id;
            this.userName = userName;
            this.emails = emails;
            this.password = password;
            this.givenName = givenName;
            this.familyName = familyName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

/*{
    "emails": [
        "kim@gmail.com"
    ],
    "meta": {
        "created": "2022-08-06T20:13:57.925Z",
        "location": "https://localhost:9445/scim2/Users/1030d5c1-93b0-4795-b4ae-20b128efd706",
        "lastModified": "2022-08-06T20:13:57.925Z",
        "resourceType": "User"
    },
    "schemas": [
        "urn:ietf:params:scim:schemas:core:2.0:User",
        "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    ],
    "roles": [
        {
            "type": "default",
            "value": "Internal/everyone"
        },
        {
            "display": "everyone"
        }
    ],
    "name": {
        "givenName": "Kim",
        "familyName": "Berry"
    },
    "id": "1030d5c1-93b0-4795-b4ae-20b128efd706",
    "userName": "kim"
    }*/
}
