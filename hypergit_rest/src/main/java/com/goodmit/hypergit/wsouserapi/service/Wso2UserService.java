package com.goodmit.hypergit.wsouserapi.service;

import org.springframework.stereotype.Component;


@Component
public class Wso2UserService {

    // GET 사용자 ID 조회
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // return : User List
    //
    /*{
        "totalResults": 2,
            "startIndex": 1,
            "itemsPerPage": 2,
            "schemas": [
        "urn:ietf:params:scim:api:messages:2.0:ListResponse"
    ],
        "Resources": [
        {
            "emails": [
            "bhkim@pharosinfo.com"
            ],
            "meta": {
            "created": "2022-07-29T11:36:20.902Z",
                    "location": "https://localhost:9445/scim2/Users/6f9dded3-62b6-48dc-884e-ff65429fc6da",
                    "lastModified": "2022-08-04T15:39:52.778Z",
                    "resourceType": "User"
        },
            "roles": [
            {
                "display": "everyone"
            }
            ],
            "name": {
            "givenName": "111",
                    "familyName": "111"
        },
            "id": "6f9dded3-62b6-48dc-884e-ff65429fc6da",
                "userName": "450192",
                "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User": {
            "validTimeStamp": "222222",
                    "organization": "PharosInfo",
                    "rgEN": "sadmi22222n2"
        }
        },
        {
            "meta": {
            "location": "https://localhost:9445/scim2/Users/84de8321-c3af-45a9-90e2-fe208468c9a6",
                    "resourceType": "User"
        },
            "roles": [
            {
                "display": "admin",
                    "value": "84802411-0d8c-4e69-a390-9f99a1c5ca69",
                    "$ref": "https://localhost:9445/scim2/Roles/84802411-0d8c-4e69-a390-9f99a1c5ca69"
            },
            {
                "display": "Application/Console",
                    "value": "a125f938-d2ae-45a0-af61-b1bfcb8ff457",
                    "$ref": "https://localhost:9445/scim2/Roles/a125f938-d2ae-45a0-af61-b1bfcb8ff457"
            },
            {
                "display": "Application/My Account",
                    "value": "df10ba8c-b978-4f0e-b262-c48051605b0e",
                    "$ref": "https://localhost:9445/scim2/Roles/df10ba8c-b978-4f0e-b262-c48051605b0e"
            },
            {
                "display": "Application/OIDC Sample",
                    "value": "4d36fe5f-064e-4e87-b0e4-b00389176a87",
                    "$ref": "https://localhost:9445/scim2/Roles/4d36fe5f-064e-4e87-b0e4-b00389176a87"
            },
            {
                "display": "Application/SALMTest",
                    "value": "38800cb7-5271-4b4f-88da-1f4c2df95bea",
                    "$ref": "https://localhost:9445/scim2/Roles/38800cb7-5271-4b4f-88da-1f4c2df95bea"
            },
            {
                "display": "everyone"
            }
            ],
            "groups": [
            {
                "display": "admin",
                    "value": "0038f128-63fc-4726-9212-68349c9f73d8",
                    "$ref": "https://localhost:9445/scim2/Groups/0038f128-63fc-4726-9212-68349c9f73d8"
            }
            ],
            "id": "84de8321-c3af-45a9-90e2-fe208468c9a6",
                "userName": "admin"
        }
    ]
    }*/



        // PATCH 사용자 ID 수정(serviceid/validtimestamp 정보 수정)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/4cba4705-b366-45a6-8ab2-2dee0f34bde3
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    /*{
        "schemas": [
        "urn:ietf:params:scim:api:messages:2.0:PatchOp"
  ],
        "Operations": [
        {
            "op": "replace",
                "value": {
            "phoneNumbers": [
            {
                "value": "1111",
                    "type": "mobile"
            }
        ]
        }
        }
  ]
    }*/
    // return : User 수정
    // 수정 필요
    /*{
        "schemas": [
        "urn:ietf:params:scim:api:messages:2.0:Error"
    ],
        "detail": "No user with the id : 4cba4705-b366-45a6-8ab2-2dee0f34bde3 in the user store.",
            "status": "404"
    }*/


    // DEL 사용자 ID 석제
    // PATCH 사용자 ID 수정(serviceid/validtimestamp 정보 수정)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/4cba4705-b366-45a6-8ab2-2dee0f34bde3
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    /*{
        "schemas": [
        "urn:ietf:params:scim:api:messages:2.0:PatchOp"
  ],
        "Operations": [
        { "op": "remove", "path": "phoneNumbers" }
  ]
    }*/
    // return : User 삭제
    // 테스트 필요

    //Post 유저 생성 (기본 )
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    /*{
        "schemas": [],
        "userName": "lhlee",
        "password": "dldPwns09"
    }*/
    // return : User 입력
    // 테스트 필요




    //Post 유저 생성 ( Name , email)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    /*{
        "schemas": [],

        "name":     {    "givenName": "Kim",    "familyName": "Berry"  },
        "userName":    "kim",
            "password":   "abc123",
            "emails":    ["kim@gmail.com"]

    }*/
    // return : User 입력
    // {
    //    "emails": [
    //        "kim@gmail.com"
    //    ],
    //    "meta": {
    //        "created": "2022-08-06T19:40:35.945Z",
    //        "location": "https://localhost:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564",
    //        "lastModified": "2022-08-06T19:40:35.945Z",
    //        "resourceType": "User"
    //    },
    //    "schemas": [
    //        "urn:ietf:params:scim:schemas:core:2.0:User",
    //        "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    //    ],
    //    "roles": [
    //        {
    //            "type": "default",
    //            "value": "Internal/everyone"
    //        },
    //        {
    //            "display": "everyone"
    //        }
    //    ],
    //    "name": {
    //        "givenName": "Kim",
    //        "familyName": "Berry"
    //    },
    //    "id": "58fcf251-8fe5-408d-944b-54fa008bb564",
    //    "userName": "kim"
    //}


    // Path 유저 수정 (add/name)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    /*{
          "schemas": [
            "urn:ietf:params:scim:api:messages:2.0:PatchOp"
          ],
          "Operations": [
            {
             "op": "add",
              "value": {
                "name": {
                    "givenName": "John",
                    "familyName": "doe"
                }
              }
            }
          ]
        }*/
    // return : User 수정 add name 입력
    // {
    //    "emails": [
    //        "kim@gmail.com"
    //    ],
    //    "meta": {
    //        "created": "2022-08-06T19:40:35.945Z",
    //        "location": "https://localhost:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564",
    //        "lastModified": "2022-08-06T19:43:32.184Z",
    //        "resourceType": "User"
    //    },
    //    "schemas": [
    //        "urn:ietf:params:scim:schemas:core:2.0:User",
    //        "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    //    ],
    //    "roles": [
    //        {
    //            "type": "default",
    //            "value": "Internal/everyone"
    //        },
    //        {
    //            "display": "everyone"
    //        }
    //    ],
    //    "name": {
    //        "givenName": "John",
    //        "familyName": "doe"
    //    },
    //    "id": "58fcf251-8fe5-408d-944b-54fa008bb564",
    //    "userName": "kim"
    //}


    // Path 유저 수정 (add/email)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    /*{
       "schemas": [
            "urn:ietf:params:scim:api:messages:2.0:PatchOp"
          ],
          "Operations": [
            {
             "op": "add",
              "value": {
                "emails": [
                    "test@daum.net"
                ]
              }
            }
          ]
        }*/
    // return : User 수정 add email 입력 확인 필요
    // {
    //    "emails": [
    //        "kim@gmail.com"
    //    ],
    //    "meta": {
    //        "created": "2022-08-06T19:40:35.945Z",
    //        "location": "https://localhost:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564",
    //        "lastModified": "2022-08-06T19:45:46.270Z",
    //        "resourceType": "User"
    //    },
    //    "schemas": [
    //        "urn:ietf:params:scim:schemas:core:2.0:User",
    //        "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    //    ],
    //    "roles": [
    //        {
    //            "type": "default",
    //            "value": "Internal/everyone"
    //        },
    //        {
    //            "display": "everyone"
    //        }
    //    ],
    //    "name": {
    //        "givenName": "John",
    //        "familyName": "doe"
    //    },
    //    "id": "58fcf251-8fe5-408d-944b-54fa008bb564",
    //    "userName": "kim"
    //}


    // Path 유저 수정 (replace/name)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    /*{
          "schemas": [
            "urn:ietf:params:scim:api:messages:2.0:PatchOp"
          ],
          "Operations": [
            {
             "op": "replace",
              "value": {
                "name": {
                    "givenName": "Jane",
                    "familyName": "lee"
                }
              }
            }
          ]
        }*/
    // return : User 수정 replace/name
    // {
    //    "emails": [
    //        "kim@gmail.com"
    //    ],
    //    "meta": {
    //        "created": "2022-08-06T19:40:35.945Z",
    //        "location": "https://localhost:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564",
    //        "lastModified": "2022-08-06T19:51:14.280Z",
    //        "resourceType": "User"
    //    },
    //    "schemas": [
    //        "urn:ietf:params:scim:schemas:core:2.0:User",
    //        "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    //    ],
    //    "roles": [
    //        {
    //            "type": "default",
    //            "value": "Internal/everyone"
    //        },
    //        {
    //            "display": "everyone"
    //        }
    //    ],
    //    "name": {
    //        "givenName": "Jane",
    //        "familyName": "lee"
    //    },
    //    "id": "58fcf251-8fe5-408d-944b-54fa008bb564",
    //    "userName": "kim"
    //}


    // Path 유저 수정 (replace/name,mobile)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    /*{
          "schemas": [
            "urn:ietf:params:scim:api:messages:2.0:PatchOp"
          ],
          "Operations": [
            {
             "op": "replace",
              "value": {
                "name": {
                    "givenName": "Jane",
                    "familyName": "Jang"
                }
              }
            },
            {
             "op": "replace",
              "value": {
                "phoneNumbers": [
                    {
                        "value": "1111",
                        "type": "mobile"
                    }
                ]
              }
            }
          ]
        }*/
    // return : User 수정 replace/name
    // {
    //    "emails": [
    //        "kim@gmail.com"
    //    ],
    //    "meta": {
    //        "created": "2022-08-06T19:40:35.945Z",
    //        "location": "https://localhost:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564",
    //        "lastModified": "2022-08-06T19:53:11.496Z",
    //        "resourceType": "User"
    //    },
    //    "schemas": [
    //        "urn:ietf:params:scim:schemas:core:2.0:User",
    //        "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    //    ],
    //    "roles": [
    //        {
    //            "type": "default",
    //            "value": "Internal/everyone"
    //        },
    //        {
    //            "display": "everyone"
    //        }
    //    ],
    //    "name": {
    //        "givenName": "Jane",
    //        "familyName": "Jang"
    //    },
    //    "id": "58fcf251-8fe5-408d-944b-54fa008bb564",
    //    "userName": "kim",
    //    "phoneNumbers": [
    //        {
    //            "type": "mobile",
    //            "value": "1111"
    //        }
    //    ]
    //}

    // Path 유저 수정 (add email/replace mobile)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    /*{
          "schemas": [
            "urn:ietf:params:scim:api:messages:2.0:PatchOp"
          ],
          "Operations": [
            {
             "op": "add",
              "value": {
                "emails": [
                    "test2@test.com"
                ]
              }
            },
            {
             "op": "replace",
              "value": {
                   "phoneNumbers": [
                    {
                        "value": "3333",
                        "type": "mobile"
                    }
                ]
              }
            }
          ]
        }*/
    // return : User 수정 replace/name
    // {
    //    "emails": [
    //        "test2@test.com"
    //    ],
    //    "meta": {
    //        "created": "2022-08-06T19:40:35.945Z",
    //        "location": "https://localhost:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564",
    //        "lastModified": "2022-08-06T19:55:20.969Z",
    //        "resourceType": "User"
    //    },
    //    "schemas": [
    //        "urn:ietf:params:scim:schemas:core:2.0:User",
    //        "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    //    ],
    //    "roles": [
    //        {
    //            "type": "default",
    //            "value": "Internal/everyone"
    //        },
    //        {
    //            "display": "everyone"
    //        }
    //    ],
    //    "name": {
    //        "givenName": "Jane",
    //        "familyName": "Jang"
    //    },
    //    "id": "58fcf251-8fe5-408d-944b-54fa008bb564",
    //    "userName": "kim",
    //    "phoneNumbers": [
    //        {
    //            "type": "mobile",
    //            "value": "3333"
    //        }
    //    ]
    //}

    // Path 유저 수정 (remove/nickName)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    //{
    //  "schemas": [
    //    "urn:ietf:params:scim:api:messages:2.0:PatchOp"
    //  ],
    //  "Operations": [
    //	{ "op": "remove",
    //	  "path": "nickName"
    //	}
    //  ]
    //}
    // return : User 수정 remove/nickName 확인 필요
    //{
    //    "schemas": [
    //        "urn:ietf:params:scim:api:messages:2.0:Error"
    //    ],
    //    "scimType": "invalidPath",
    //    "detail": "No such attribute with the name : nickName in the current resource",
    //    "status": "400"
    //}


    // Path 유저 수정 (remove/givenName)(familyName은 불가)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    //{
    //  "schemas": [
    //    "urn:ietf:params:scim:api:messages:2.0:PatchOp"
    //  ],
    //  "Operations": [
    //{ "op": "remove", "path": "name.givenName" }
    //
    //  ]
    //}
    // return : User 수정 remove/givenName)(familyName은 불가)
    //{
    //    "emails": [
    //        "test2@test.com"
    //    ],
    //    "meta": {
    //        "created": "2022-08-06T19:40:35.945Z",
    //        "location": "https://localhost:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564",
    //        "lastModified": "2022-08-06T19:59:04.893Z",
    //        "resourceType": "User"
    //    },
    //    "schemas": [
    //        "urn:ietf:params:scim:schemas:core:2.0:User",
    //        "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    //    ],
    //    "roles": [
    //        {
    //            "type": "default",
    //            "value": "Internal/everyone"
    //        },
    //        {
    //            "display": "everyone"
    //        }
    //    ],
    //    "name": {
    //        "familyName": "Jang"
    //    },
    //    "id": "58fcf251-8fe5-408d-944b-54fa008bb564",
    //    "userName": "kim",
    //    "phoneNumbers": [
    //        {
    //            "type": "mobile",
    //            "value": "3333"
    //        }
    //    ]
    //}

    // Path 유저 수정 (remove/email)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    //{
    //  "schemas": [
    //    "urn:ietf:params:scim:api:messages:2.0:PatchOp"
    //  ],
    //  "Operations": [
    //{ "op": "remove", "path": "emails" }
    //
    //  ]
    //}
    // return : User 수정 remove/givenName)(familyName은 불가)
    //{
    //    "meta": {
    //        "created": "2022-08-06T19:40:35.945Z",
    //        "location": "https://localhost:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564",
    //        "lastModified": "2022-08-06T20:02:47.069Z",
    //        "resourceType": "User"
    //    },
    //    "schemas": [
    //        "urn:ietf:params:scim:schemas:core:2.0:User",
    //        "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    //    ],
    //    "roles": [
    //        {
    //            "type": "default",
    //            "value": "Internal/everyone"
    //        },
    //        {
    //            "display": "everyone"
    //        }
    //    ],
    //    "name": {
    //        "givenName": "Jane",
    //        "familyName": "lee"
    //    },
    //    "id": "58fcf251-8fe5-408d-944b-54fa008bb564",
    //    "userName": "kim",
    //    "phoneNumbers": [
    //        {
    //            "type": "mobile",
    //            "value": "3333"
    //        }
    //    ]
    //}
    // Path 유저 수정 (remove/mobile)
    // URL : https://wso2.uiscloud.com:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564
    // Basic Auth : Authorization   : Basic YWRtaW46YWRtaW4=
    // Input parm :
    //{
    //  "schemas": [
    //    "urn:ietf:params:scim:api:messages:2.0:PatchOp"
    //  ],
    //  "Operations": [
    //{ "op": "remove", "path": "phoneNumbers" }
    //  ]
    //}
    // return : User 수정 remove/givenName)(familyName은 불가)
    //{
    //    "meta": {
    //        "created": "2022-08-06T19:40:35.945Z",
    //        "location": "https://localhost:9445/scim2/Users/58fcf251-8fe5-408d-944b-54fa008bb564",
    //        "lastModified": "2022-08-06T20:11:43.778Z",
    //        "resourceType": "User"
    //    },
    //    "schemas": [
    //        "urn:ietf:params:scim:schemas:core:2.0:User",
    //        "urn:ietf:params:scim:schemas:extension:enterprise:2.0:User"
    //    ],
    //    "roles": [
    //        {
    //            "type": "default",
    //            "value": "Internal/everyone"
    //        },
    //        {
    //            "display": "everyone"
    //        }
    //    ],
    //    "name": {
    //        "givenName": "Jane",
    //        "familyName": "lee"
    //    },
    //    "id": "58fcf251-8fe5-408d-944b-54fa008bb564",
    //    "userName": "kim"
    //}




}
