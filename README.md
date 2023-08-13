# stocksAuthorization
Test project for study (stocks with auth)

This app support two type of authorization:
- by login / password
- by OAUTH2 from Google

1. Login and password
 
- At first, you need to register. Send POST request with parameters:

```
curl -X POST "http://localhost:8090/api/v1/public/register" 
     --header "Content-Type: application/json" 
     --data-raw "{\"username\":\"javaoauth2\", \"password\":\"123456\", \"email\":\"javaoauth2@gmail.com\"}"
```
If everything is successful, you will receive a 200OK response. Otherwise - error code

- The second step - getting login and getting JWT token

```
curl -X POST "http://localhost:8090/api/v1/public/login" 
     --header "Content-Type: application/json" 
     --data-raw "{\"email\":\"javaoauth2@gmail.com\",\"password\":\"123456\"}"
```

If everything is successful, you will receive a 200OK response and JWT. 

```
{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im9...xOTUzMTIwfQ.Pe1htU1zyjPrzWFFBCBMtOgyFgx2K8Kod6ssD4UuIFs"}
```

Otherwise - error code

- Access to secured pages with JWT token as Bearer token
```
curl --location "http://localhost:8090/api/v1/private/list" 
     --header "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im9...xOTUzMTIwfQ.Pe1htU1zyjPrzWFFBCBMtOgyFgx2K8Kod6ssD4UuIFs" 
```

2. Authorization with Google OAUTH2
 
- At first, you need to get JWT token from Google through POSTMAN

a) Open a new request and go to the **Authorization** tab

b) Select Type **Oauth2**

с) In opens window scroll to **Configure New Token** item and enter data:

| Field             | Value                                          | 
|-------------------|------------------------------------------------|
| Grant Type        | Authorization code                             |
| Callback URL      | http://localhost:8090/login/oauth2/code/google | 
| Auth URL          | https://accounts.google.com/o/oauth2/v2/auth   |
| Access Token URL  | https://accounts.google.com/o/oauth2/token     |
| Client ID            | Your CLIENT_ID                                 |
| Client Secret            | Your CLIENT_SECRET                             |
| Scope             | email profile                                  |
| Client Authentication            | Send as Basic Auth header                      |

Click **Get new Access Token**

d) Enter needed credentials for Google

e) As result you response Google credentials for you. But most interested it's JWT token.

f) Access to secured pages with Google JWT token as Bearer token
```
curl --location "http://localhost:8090/api/v1/private/list" 
     --header "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Im9...xOTUzMTIwfQ.Pe1htU1zyjPrzWFFBCBMtOgyFgx2K8Kod6ssD4UuIFs" 
``` 




