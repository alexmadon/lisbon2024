# jira

MNT-24542
Improve ACS REST API error messages when using Keycloak token with bad scope

# compile and run

```
cd /home/madon/hylandtools/sdk6/alex_rest_search_keycloak_token
mvn clean package -Dlicense.skip=true 
java -jar /home/madon/hylandtools/sdk6/alex_rest_search_keycloak_token/target/sdk-alex-0.0.1-SNAPSHOT.jar
/home/madon/alfrescobin/jdk-17.0.4+8/bin/java  -jar  /home/madon/hylandtools/sdk6/alex_03_rest_search_keycloak_token/target/sdk-alex-0.0.1-SNAPSHOT.jar
```


# run with spring DEBUG or TRACE if necessary

```
 java -jar /home/madon/hylandtools/sdk6/alex_03_rest_search_keycloak_token/target/sdk-alex-0.0.1-SNAPSHOT.jar
 java -Dlogging.level.root=DEBUG -jar /home/madon/hylandtools/sdk6/alex_03_rest_search_keycloak_token/target/sdk-alex-0.0.1-SNAPSHOT.jar
 java -Dlogging.level.root=TRACE -jar /home/madon/hylandtools/sdk6/alex_03_rest_search_keycloak_token/target/sdk-alex-0.0.1-SNAPSHOT.jar
```


# authentication with keycloak

Ce client Feign repose sur la configuration Spring OAuth2 pour demander un jeton d'authentification auprès de Keycloak en utilisant client_credentials (client_id + client_secret).

# project

a test project to exhibit the issue of the refresh token expiration

https://github.com/Alfresco/alfresco-java-sdk/tree/develop/samples


https://docs.alfresco.com/content-services/latest/develop/oop-sdk/#restapijavawrapperproject


# spring oauth doc


https://docs.spring.io/spring-security/site/docs/5.2.12.RELEASE/reference/html/oauth2.html
https://docs.spring.io/spring-security/site/docs/5.2.12.RELEASE/reference/html/oauth2.html#oauth2login-sample-application-config

spring.security.oauth2

https://docs.alfresco.com/content-services/latest/develop/oop-sdk/#rest-api-config



If you are using OAuth2 with Alfresco Identity Service, then you can use client-credential based authentication:

spring.security.oauth2.client.registration.alfresco-rest-api.provider=alfresco-identity-service
spring.security.oauth2.client.registration.alfresco-rest-api.client-id=clientId
spring.security.oauth2.client.registration.alfresco-rest-api.client-secret=clientSecret
spring.security.oauth2.client.registration.alfresco-rest-api.authorization-grant-type=client_credentials
spring.security.oauth2.client.provider.alfresco-identity-service.token-uri=${keycloak.auth-server-url}/auth/realms/${keycloak.realm}/protocol/openid-connect/token

we have something similar to aps with keycloak
(see okta.html)
file:///home/madon/medium/okta.html#alfresco-process-services-client-config

 Alfresco Process Services 
 
 ```
# Content services Identity service configuration
alfresco.content.sso.enabled=${keycloak.enabled}
alfresco.content.sso.client_id=${keycloak.resource}
alfresco.content.sso.client_secret=${keycloak.credentials.secret}
alfresco.content.sso.realm=${keycloak.realm}
alfresco.content.sso.scope=offline_access
alfresco.content.sso.auth_uri=${keycloak.aut
```


 # in keycloak, in the client page, Setting -> Access Type: set to confidential
            # Then, you will find the Client secret in
            # Clients > Client details > {your-client-name} > Credentials.


# keycloak for sdk


    Client ID – alfresco-adw
    Enabled – on
    Client Protocol – openid-connect (this is the protocol that the ADF app will use to communicate with Identity Service)
    Access Type – public OR confidential (see above)
    Standard Flow Enabled – off
    Implicit Flow Enabled – on
    Direct Access Grants Enabled – off
    Valid Redirect URIs – For development purposes, you can use “*” to indicate all URIs but in a production environment, you’d want this list to contain the URL(s) for your ADF app (e.g. https://adf.argondigital.com*)
    Base URL – The primary URL people use to access your ADF app. In our example, this is https://adf.argondigital.com
    Web Origins – List of hostnames that will be accessing Identity Service for the purposes of SSO. For development, you could use “*” but for production, this should be your ADF app URL (e.g. https://adf.argondigital.com*)


# error SSL

```
madon@toshibalex:~/hylandtools/sdk6/alex_03_rest_search_keycloak_token (master)$ java -jar /home/madon/hylandtools/sdk6/alex_03_rest_search_keycloak_token/target/sdk-alex-0.0.1-SNAPSHOT.jar 

2024-07-22T14:03:21.619+02:00  INFO 68102 --- [           main] .s.b.a.l.ConditionEvaluationReportLogger : 

Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2024-07-22T14:03:21.655+02:00 ERROR 68102 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.security.oauth2.client.ClientAuthorizationException: [invalid_token_response] An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: I/O error on POST request for "https://keycloak.example.foo:8443/auth/auth/realms/alfresco/protocol/openid-connect/token": PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	at org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider.getTokenResponse(ClientCredentialsOAuth2AuthorizedClientProvider.java:96) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]
	at org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider.authorize(ClientCredentialsOAuth2AuthorizedClientProvider.java:85) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]
	at org.springframework.security.oauth2.client.DelegatingOAuth2AuthorizedClientProvider.authorize(DelegatingOAuth2AuthorizedClientProvider.java:71) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]

```


solution: use custom java cacerts in securit folder that is use /home/madon/alfrescobin/jdk-17.0.4+8/bin/java

# error OAuth 2.0 Access Token Response: 404 Not Found


```
madon@toshibalex:~/hylandtools/sdk6/alex_03_rest_search_keycloak_token (master)$ /home/madon/alfrescobin/jdk-17.0.4+8/bin/java -jar /home/madon/hylandtools/sdk6/alex_03_rest_search_keycloak_token/target/sdk-alex-0.0.1-SNAPSHOT.jar 


org.springframework.security.oauth2.client.ClientAuthorizationException: [invalid_token_response] An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: 404 Not Found: "{"error":"RESTEASY003210: Could not find resource for full path: https://keycloak.example.foo:8443/auth/auth/realms/alfresco/protocol/openid-connect/token"}"
	at org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider.getTokenResponse(ClientCredentialsOAuth2AuthorizedClientProvider.java:96) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]
	at org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider.authorize(ClientCredentialsOAuth2AuthorizedClientProvider.java:85) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]
	at org.springframework.security.oauth2.client.DelegatingOAuth2AuthorizedClientProvider.authorize(DelegatingOAuth2AuthorizedClientProvider.java:71) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]
	at org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager.authorize(AuthorizedClientServiceOAuth2AuthorizedClientManager.java:144) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]
	at org.alfresco.rest.sdk.feign.oauth2.OAuth2FeignRequestInterceptor.getAuthorizationToken(OAuth2FeignRequestIntercepto
```



solution:

there is a double /auth/auth, so remove one:

```
# keycloak.auth-server-url=https://keycloak.example.foo:8443/auth/
keycloak.auth-server-url=https://keycloak.example.foo:8443/
```


# error token


```
2024-07-22T14:15:57.977+02:00 ERROR 69817 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.security.oauth2.client.ClientAuthorizationException: [invalid_token_response] An error occurred while attempting to retrieve the OAuth 2.0 Access Token Response: 401 Unauthorized: [no body]
	at org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider.getTokenResponse(ClientCredentialsOAuth2AuthorizedClientProvider.java:96) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]
	at org.springframework.security.oauth2.client.ClientCredentialsOAuth2AuthorizedClientProvider.authorize(ClientCredentialsOAuth2AuthorizedClientProvider.java:85) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]
	at org.springframework.security.oauth2.client.DelegatingOAuth2AuthorizedClientProvider.authorize(DelegatingOAuth2AuthorizedClientProvider.java:71) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]
	at org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager.authorize(AuthorizedClientServiceOAuth2AuthorizedClientManager.java:144) ~[spring-security-oauth2-client-6.2.3.jar!/:6.2.3]
	at org.alfresco.rest.sdk.feign.oauth2.OAuth2FeignRequestInterceptor.getAuthorizationToken(OAuth2FeignRequestInterceptor.java:58) ~[alfresco-java-rest-api-common-6.2.0.jar!/:6.2.0]
	at org.alfresco.rest.sdk.feign.oauth2.OAuth2FeignRequestInterceptor.apply(OAuth2FeignRequestInterceptor.java:54) ~[alfresco-java-rest-api-common-6.2.0.jar!/:6.2.0]
```

in DEBUG:

```
2024-07-22T14:19:39.877+02:00 DEBUG 70071 --- [           main] s.n.www.protocol.http.HttpURLConnection  : sun.net.www.MessageHeader@53f0d09c8 pairs: {POST /auth/realms/alfresco/protocol/openid-connect/token HTTP/1.1: null}{Accept: application/json;charset=UTF-8}{Content-Type: application/x-www-form-urlencoded;charset=UTF-8}{Authorization: Basic YWxmcmVzY28tc2RrOjdlZ2J5alR2V3V6S05ONVJVN0dhbnBwMUpkeVVlOE5h}{User-Agent: Java/17.0.4}{Host: keycloak.example.foo:8443}{Connection: keep-alive}{Content-Length: 29}

```

alfresco-sdk:7egbyjTvWuzKNN5RU7Ganpp1JdyUe8Na

curl -X POST -H 'Authorization: Basic alfresco-sdk:7egbyjTvWuzKNN5RU7Ganpp1JdyUe8Na' https://keycloak.example.foo:8443/auth/realms/alfresco/protocol/openid-connect/token
curl -X POST -H 'Authorization: Basic alfresco-sdk:7egbyjTvWuzKNN5RU7Ganpp1JdyUe8Na' https://keycloak.example.foo:8443/auth/realms/alfresco/protocol/openid-connect/token -k
{"error":"invalid_request","error_description":"Missing form parameter: grant_type"}



curl -X POST -H 'Authorization: Basic YWxmcmVzY28tc2RrOjdlZ2J5alR2V3V6S05ONVJVN0dhbnBwMUpkeVVlOE5h' https://keycloak.example.foo:8443/auth/realms/alfresco/protocol/openid-connect/token -k -d 'client_credentials' 



tcpdump on lo in HTTP:

```
POST /auth/realms/alfresco/protocol/openid-connect/token HTTP/1.1
Accept: application/json;charset=UTF-8
Content-Type: application/x-www-form-urlencoded;charset=UTF-8
Authorization: Basic YWxmcmVzY28tc2RrOjdlZ2J5alR2V3V6S05ONVJVN0dhbnBwMUpkeVVlOE5h
User-Agent: Java/17.0.4
Host: 127.0.0.2:8180
Connection: keep-alive
Content-Length: 29

grant_type=client_credentials

HTTP/1.1 401 Unauthorized
Referrer-Policy: no-referrer
X-Frame-Options: SAMEORIGIN
Strict-Transport-Security: max-age=31536000; includeSubDomains
Cache-Control: no-store
X-Content-Type-Options: nosniff
Pragma: no-cache
X-XSS-Protection: 1; mode=block
Content-Type: application/json
content-length: 100

{"error":"unauthorized_client","error_description":"Client not enabled to retrieve service account"}

```

curl -X POST -H 'Authorization: Basic YWxmcmVzY28tc2RrOjdlZ2J5alR2V3V6S05ONVJVN0dhbnBwMUpkeVVlOE5h' https://keycloak.example.foo:8443/auth/realms/alfresco/protocol/openid-connect/token -k -d 'grant_type=client_credentials' 
{"error":"unauthorized_client","error_description":"Client not enabled to retrieve service account"}


https://stackoverflow.com/questions/52238839/access-keycloak-rest-admin-api-using-a-service-account-client-credential-grant

https://www.keycloak.org/docs/latest/server_admin/index.html#_service_accounts


curl -X POST --user service-account-alfresco-sdk:7egbyjTvWuzKNN5RU7Ganpp1JdyUe8Na  https://keycloak.example.foo:8443/auth/realms/alfresco/protocol/openid-connect/token -k -d 'grant_type=client_credentials' 




THIS WORKS:


```
curl -X POST --user alfresco-sdk:7egbyjTvWuzKNN5RU7Ganpp1JdyUe8Na  https://keycloak.example.foo:8443/auth/realms/alfresco/protocol/openid-connect/token -k -d 'grant_type=client_credentials' -s  | jq
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI1N3pKMDFTb0tqaTZBTzFKVkhhVFRib1NLQUFmb29KcXlPZnJZZ2FXa0xjIn0.eyJleHAiOjE3MjE2NTM0OTEsImlhdCI6MTcyMTY1MzE5MSwianRpIjoiMWM2MDkwNzYtNDI4Ni00ODMwLWExMDUtYjhhOGFmMzFlMThjIiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5leGFtcGxlLmZvbzo4NDQzL2F1dGgvcmVhbG1zL2FsZnJlc2NvIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImU1YjNhNWQ4LTEyZGQtNGQyMS1hOGYyLTFlZjRiZDQyMjI1ZCIsInR5cCI6IkJlYXJlciIsImF6cCI6ImFsZnJlc2NvLXNkayIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWFsZnJlc2NvIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiY2xpZW50SG9zdCI6IjEyNy4wLjAuMSIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1hbGZyZXNjby1zZGsiLCJjbGllbnRBZGRyZXNzIjoiMTI3LjAuMC4xIiwiY2xpZW50X2lkIjoiYWxmcmVzY28tc2RrIn0.Pr8yVaUf1Wf6gTCj_mcpE_A8_3SO6v5g-XDEi32A7eyC6uqxrqzOjjVDwjKiSCo8bcA5H2mJGBt7D1hmnsMsM7begfJaE5e-u1eESFu790m1BXdtnu8SVTe70xREZO2Q8OIft5ySmeFrcdUnUaCFyE1-ZtL8Q2fNX93gAUskoDNhEsvwUQhE8GfxSieF0f1iCgTlrzqBtVAckY_G_ZC2MaAqRF-6aFjyMhrbfUgz3_VebkOOMxo6U-ZX1o5iJquhsLGwMEfzyQ7RNuFpy7JCNMeS4pKj0b_gQA2Oyacrj66H3C3FEYNk40nOIbZ3_N3kdqquKgTZFuZBt88EnzhTaQ",
  "expires_in": 300,
  "refresh_expires_in": 0,
  "token_type": "Bearer",
  "not-before-policy": 0,
  "scope": "email profile"
}
```



```
curl -X POST --user alfresco-sdk:7egbyjTvWuzKNN5RU7Ganpp1JdyUe8Na  http://127.0.0.2:8180/auth/realms/alfresco/protocol/openid-connect/token -k -d 'grant_type=client_credentials' -s  | jq
{
  "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI1N3pKMDFTb0tqaTZBTzFKVkhhVFRib1NLQUFmb29KcXlPZnJZZ2FXa0xjIn0.eyJleHAiOjE3MjE2NTM1ODksImlhdCI6MTcyMTY1MzI4OSwianRpIjoiMzgzZDc0YjEtMjY0Zi00ZmVlLTkzZmItZGRjYzllNWRlYzM4IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5leGFtcGxlLmZvbzo4NDQzL2F1dGgvcmVhbG1zL2FsZnJlc2NvIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImU1YjNhNWQ4LTEyZGQtNGQyMS1hOGYyLTFlZjRiZDQyMjI1ZCIsInR5cCI6IkJlYXJlciIsImF6cCI6ImFsZnJlc2NvLXNkayIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWFsZnJlc2NvIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiY2xpZW50SG9zdCI6IjEyNy4wLjAuMSIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1hbGZyZXNjby1zZGsiLCJjbGllbnRBZGRyZXNzIjoiMTI3LjAuMC4xIiwiY2xpZW50X2lkIjoiYWxmcmVzY28tc2RrIn0.YY-TlcYEee5M3XCloEpq76NvqxwY3_FYGcXhBK6dnW04Ti182oSLMfPFeuHVZHjzV5MBgbo8tZqfpGYGvxFo8j_0wEdQ8_QH6F4l3YEi-_ZhZ_2xhLFeSdWYOruigxOBLCX-aLpK1eYVY0zgGmpi_i7abOahnTikaD1Q53gYO7S8r6Q0RWGBxbLD2PZV716mUdru72zhJ-aJ94r0pAfc79SlL-jTRIrUQ6M7-EpbFluhlroBPZbrCnIrqzmRLDsHQSgJh5GFY6k37rB-lby5s08Zq23BFr9O4Cc-PthZybQ_Jrk7_99G4pjefSkIIEyBbAkgZxsLv8LgnIB8Sney6w",
  "expires_in": 300,
  "refresh_expires_in": 0,
  "token_type": "Bearer",
  "not-before-policy": 0,
  "scope": "email profile"
}
```


# error 401 on search API



frist get the bearer:


```
POST /auth/realms/alfresco/protocol/openid-connect/token HTTP/1.1
Accept: application/json;charset=UTF-8
Content-Type: application/x-www-form-urlencoded;charset=UTF-8
Authorization: Basic YWxmcmVzY28tc2RrOjdlZ2J5alR2V3V6S05ONVJVN0dhbnBwMUpkeVVlOE5h
User-Agent: Java/17.0.4
Host: 127.0.0.2:8180
Connection: keep-alive
Content-Length: 29

grant_type=client_credentials


HTTP/1.1 200 OK
Referrer-Policy: no-referrer
X-Frame-Options: SAMEORIGIN
Strict-Transport-Security: max-age=31536000; includeSubDomains
Cache-Control: no-store
X-Content-Type-Options: nosniff
Set-Cookie: KC_RESTART=; Version=1; Expires=Thu, 01-Jan-1970 00:00:10 GMT; Max-Age=0; Path=/auth/realms/alfresco/; HttpOnly
Pragma: no-cache
X-XSS-Protection: 1; mode=block
Content-Type: application/json
content-length: 1421

{"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI1N3pKMDFTb0tqaTZBTzFKVkhhVFRib1NLQUFmb29KcXlPZnJZZ2FXa0xjIn0.eyJleHAiOjE3MjE2NTM5NTcsImlhdCI6MTcyMTY1MzY1NywianRpIjoiNDVjZmIyOWYtNDRhNi00N2Y3LWJkY2EtMmNlMzk2ZWYyN2Q4IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5leGFtcGxlLmZvbzo4NDQzL2F1dGgvcmVhbG1zL2FsZnJlc2NvIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImU1YjNhNWQ4LTEyZGQtNGQyMS1hOGYyLTFlZjRiZDQyMjI1ZCIsInR5cCI6IkJlYXJlciIsImF6cCI6ImFsZnJlc2NvLXNkayIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWFsZnJlc2NvIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiY2xpZW50SG9zdCI6IjEyNy4wLjAuMSIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1hbGZyZXNjby1zZGsiLCJjbGllbnRBZGRyZXNzIjoiMTI3LjAuMC4xIiwiY2xpZW50X2lkIjoiYWxmcmVzY28tc2RrIn0.NSE-vjK5WJbchiTEGW5oibao4teDdJYPL_dmhVRUTIQu39cXnz95W6tfnzaiviuVo5r0Pk0kChF-mwj934kz7USbE8YQWnIbrTMY3fVixP3rUu4QF70kjb3KqYmUv_qgCXDQruGgzI-kH0uG1gWA_luZazvwa61FdvlXVg3AB32ZfbsGj34Yr6lrY-XJTc5i4zY5EZuL1GRJfJZyREzciTqufpN_hTvkau-ZAbXb37Bizzk3wyVTEtP-khGXmn5pCc4I8RQHZInAivbUh7fgVeBsGHRZEy2iraaLn2GrpFcDpazDdCOtsm1qYUsMRt3pqr95gJZW3cpKNZAblDaD3g","expires_in":300,"refresh_expires_in":0,"token_type":"Bearer","not-before-policy":0,"scope":"email profile"}
```


then call the API


```
POST /alfresco/api/-default-/public/search/versions/1/search HTTP/1.1
Accept: application/json
Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI1N3pKMDFTb0tqaTZBTzFKVkhhVFRib1NLQUFmb29KcXlPZnJZZ2FXa0xjIn0.eyJleHAiOjE3MjE2NTM5NTcsImlhdCI6MTcyMTY1MzY1NywianRpIjoiNDVjZmIyOWYtNDRhNi00N2Y3LWJkY2EtMmNlMzk2ZWYyN2Q4IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5leGFtcGxlLmZvbzo4NDQzL2F1dGgvcmVhbG1zL2FsZnJlc2NvIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6ImU1YjNhNWQ4LTEyZGQtNGQyMS1hOGYyLTFlZjRiZDQyMjI1ZCIsInR5cCI6IkJlYXJlciIsImF6cCI6ImFsZnJlc2NvLXNkayIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJkZWZhdWx0LXJvbGVzLWFsZnJlc2NvIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiY2xpZW50SG9zdCI6IjEyNy4wLjAuMSIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC1hbGZyZXNjby1zZGsiLCJjbGllbnRBZGRyZXNzIjoiMTI3LjAuMC4xIiwiY2xpZW50X2lkIjoiYWxmcmVzY28tc2RrIn0.NSE-vjK5WJbchiTEGW5oibao4teDdJYPL_dmhVRUTIQu39cXnz95W6tfnzaiviuVo5r0Pk0kChF-mwj934kz7USbE8YQWnIbrTMY3fVixP3rUu4QF70kjb3KqYmUv_qgCXDQruGgzI-kH0uG1gWA_luZazvwa61FdvlXVg3AB32ZfbsGj34Yr6lrY-XJTc5i4zY5EZuL1GRJfJZyREzciTqufpN_hTvkau-ZAbXb37Bizzk3wyVTEtP-khGXmn5pCc4I8RQHZInAivbUh7fgVeBsGHRZEy2iraaLn2GrpFcDpazDdCOtsm1qYUsMRt3pqr95gJZW3cpKNZAblDaD3g
Content-Type: application/json
User-Agent: Java/17.0.4
Host: localhost:8080
Connection: keep-alive
Content-Length: 379

{"query":{"language":"afts","userQuery":null,"query":"TEXT:*"},"paging":null,"include":null,"includeRequest":false,"fields":null,"sort":null,"templates":null,"defaults":null,"localization":null,"filterQueries":null,"facetQueries":null,"facetFields":null,"facetIntervals":null,"pivots":null,"stats":null,"spellcheck":null,"scope":null,"limits":null,"highlight":null,"ranges":null}



HTTP/1.1 401 
X-Frame-Options: SAMEORIGIN
WWW-Authenticate: AlfTicket realm="Alfresco -default- tenant"
Cache-Control: no-cache
Expires: Thu, 01 Jan 1970 00:00:00 GMT
Pragma: no-cache
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 22 Jul 2024 13:07:37 GMT
Keep-Alive: timeout=20
Connection: keep-alive


{"error":{"errorKey":"framework.exception.ApiDefault","statusCode":401,"briefSummary":"06220002 Authorization 'Bearer' not supported.","stackTrace":"For security reasons the stack trace is no longer displayed, but the property is kept for previous versions","descriptionURL":"https://api-explorer.alfresco.com","logId":"9642c6f7-cf10-43a4-82a7-69f1c2a9172a"}}



```


in alfresco catalina.out


```
2024-07-22T15:14:13,972 [] DEBUG [authentication.identityservice.SpringBasedIdentityServiceFacade] [http-nio-127.0.0.1-8080-exec-1] Bearer token outcome: {sub=e5b3a5d8-12dd-4d21-a8f2-1ef4bd42225d, resource_access={account={roles=[manage-account, manage-account-links, view-profile]}}, email_verified=false, clientHost=127.0.0.1, allowed-origins=[*], iss=https://keycloak.example.foo:8443/auth/realms/alfresco, typ=Bearer, preferred_username=service-account-alfresco-sdk, clientAddress=127.0.0.1, client_id=alfresco-sdk, aud=[account], realm_access={roles=[default-roles-alfresco, offline_access, uma_authorization]}, azp=alfresco-sdk, scope=email profile, exp=2024-07-22T13:19:13Z, iat=2024-07-22T13:14:13Z, jti=bd97e67b-802c-48f5-9c09-8a98cf7e38a4}
2024-07-22T15:14:13,995 [] ERROR [authentication.identityservice.IdentityServiceRemoteUserMapper] [http-nio-127.0.0.1-8080-exec-1] Failed to authenticate user using IdentityServiceRemoteUserMapper: class com.nimbusds.openid.connect.sdk.UserInfoErrorResponse cannot be cast to class com.nimbusds.openid.connect.sdk.UserInfoSuccessResponse (com.nimbusds.openid.connect.sdk.UserInfoErrorResponse and com.nimbusds.openid.connect.sdk.UserInfoSuccessResponse are in unnamed module of loader org.apache.catalina.loader.ParallelWebappClassLoader @426b6a74)
java.lang.ClassCastException: class com.nimbusds.openid.connect.sdk.UserInfoErrorResponse cannot be cast to class com.nimbusds.openid.connect.sdk.UserInfoSuccessResponse (com.nimbusds.openid.connect.sdk.UserInfoErrorResponse and com.nimbusds.openid.connect.sdk.UserInfoSuccessResponse are in unnamed module of loader org.apache.catalina.loader.ParallelWebappClassLoader @426b6a74)
```

COMPARE:


```
[authentication.identityservice.SpringBasedIdentityServiceFacade] [http-nio-127.0.0.1-8080-exec-7] 
Bearer token outcome: {sub=e5b3a5d8-12dd-4d21-a8f2-1ef4bd42225d, resource_access={account={roles=[manage-account, manage-account-links, view-profile]}}, email_verified=true, clientHost=127.0.0.1, allowed-origins=[*], iss=https://keycloak.example.foo:8443/auth/realms/alfresco, typ=Bearer, preferred_username=service-account-alfresco-sdk, given_name=, clientAddress=127.0.0.1, client_id=alfresco-sdk, aud=[account], realm_access={roles=[default-roles-alfresco, offline_access, uma_authorization]}, azp=alfresco-sdk, scope=email profile, exp=2024-07-22T14:15:12Z, iat=2024-07-22T14:10:12Z, family_name=, jti=7fd72e39-ce28-4ab1-8219-34fb57b239be, email=service-account-alfresco-sdk@madon.net}
```

WITH:


```
2024-07-22T16:24:18,586 [] DEBUG [authentication.identityservice.SpringBasedIdentityServiceFacade] [http-nio-127.0.0.1-8080-exec-8] 
Bearer token outcome: {sub=60a9b6c5-64ef-405f-8c6f-66bd8cd3878a, resource_access={realm-management={roles=[view-realm, manage-realm, manage-users, view-users, view-clients, query-clients, manage-clients, query-groups, query-users]}, account={roles=[manage-account, manage-account-links, view-profile]}}, email_verified=true, allowed-origins=[*], iss=https://keycloak.example.foo:8443/auth/realms/alfresco, typ=Bearer, preferred_username=admin, locale=en, given_name=admin, nonce=REZGbFZtcDJaTHJfckZUaUxpdGlNYzdjYVR4a0RuVEdSd2ZUdktZTzV2WWo4, sid=d7a3bcf3-c44e-4668-83a8-890e13ce8eac, aud=[realm-management, account], realm_access={roles=[offline_access, uma_authorization, user]}, azp=alfresco-adw, auth_time=2024-07-22T14:23:37Z, scope=openid email profile, name=admin admin, exp=2024-07-22T14:38:37Z, session_state=d7a3bcf3-c44e-4668-83a8-890e13ce8eac, iat=2024-07-22T14:23:37Z, family_name=admin, jti=0102ed04-17d7-46ea-bbe1-895e745ab301, email=admin@app.activiti.com}
```




so create a user

service-account-alfresco-sdk
service-account-alfresco-sdk@madon.net
verified email: true

you cannot create a new user, as it says it is created.
Insteal go to sdk client alfresco-sdk -> service accounts role
To manage detail and group mappings, click on the username service-account-alfresco-sdk
set Email verifiedt to true

https://github.com/Acosix/alfresco-keycloak/blob/master/docs/Simple-Configuration.md


Setting Up Alfresco Identity Service with Okta
ArgonDigital
https://argondigital.com › ecm › co...
·
Traduire cette page
Here's how this needs to be configured: A new internal user should be created in APS. We'll call it “activiti-admin-service-account@foo.com” ...

How to import users on startup in Keycloak 19
Discourse
https://keycloak.discourse.group › ...
·
Traduire cette page
12 déc. 2022 — “serviceAccountClientId”: “alfresco”, “username”: “service-account-alfresco”, “enabled”: true, “email”: “service-account-alfresco@muster.com”,



# errors


```
madon@toshibalex:~/hylandtools/sdk6/alextoken (master)$ mvn clean package -Dlicense.skip=true
[INFO] Scanning for projects...
Downloading from central: https://repo.maven.apache.org/maven2/org/alfresco/alfresco-java-sdk-samples/6.2.0/alfresco-java-sdk-samples-6.2.0.pom
[ERROR] [ERROR] Some problems were encountered while processing the POMs:
[FATAL] Non-resolvable parent POM for net.madon:madon-sdk-sample:1.0: Could not find artifact org.alfresco:alfresco-java-sdk-samples:pom:6.2.0 in central (https://repo.maven.apache.org/maven2) and 'parent.relativePath' points at wrong local POM @ line 8, column 11
 @ 
[ERROR] The build could not read 1 project -> [Help 1]
[ERROR]   
[ERROR]   The project net.madon:madon-sdk-sample:1.0 (/home/madon/hylandtools/sdk6/alextoken/pom.xml) has 1 error
[ERROR]     Non-resolvable parent POM for net.madon:madon-sdk-sample:1.0: Could not find artifact org.alfresco:alfresco-java-sdk-samples:pom:6.2.0 in central (https://repo.maven.apache.org/maven2) and 'parent.relativePath' points at wrong local POM @ line 8, column 11 -> [Help 2]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/ProjectBuildingException
[ERROR] [Help 2] http://cwiki.apache.org/confluence/display/MAVEN/UnresolvableModelException
```


thisis fixed using:

Maven needs to know about the Alfresco Artifacts Repository (Nexus) so add the following to ~/.m2/settings.xml:

```
<repositories>
    <repository>
      <id>alfresco-public</id>
      <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
    </repository>
  
  </repositories>
```

see https://docs.alfresco.com/content-services/latest/develop/oop-sdk/#restapijavawrapperproject

https://maven.apache.org/guides/mini/guide-multiple-repositories.html


## first possibility, in pom.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns="http://maven.apache.org/POM/4.0.0"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>


  <parent>
    <groupId>org.alfresco</groupId>
    <artifactId>alfresco-java-sdk-samples</artifactId>
    <version>6.2.0</version>
  </parent>
  <!-- https://artifacts.alfresco.com/nexus/#nexus-search;quick~alfresco-java-sdk-samples -->
  <!-- NEXUS: amadon + miscrosoft passwd -->
  
  <groupId>net.madon</groupId>
  <artifactId>madon-sdk-sample</artifactId>
  <version>1.0</version>
  <packaging>pom</packaging>
  <name>Madon :: Java SDK :: Samples</name>
  <description>Alex Sample application of the Java SDK</description>

  <dependencies>
    <!-- https://artifacts.alfresco.com/nexus/#nexus-search;quick~alfresco-java-event-api-spring-boot-starter -->
    <dependency>
      <groupId>org.alfresco</groupId>
      <artifactId>alfresco-java-event-api-spring-boot-starter</artifactId>
      <version>6.2.0</version>
    </dependency>
    
    <!-- https://artifacts.alfresco.com/nexus/#nexus-search;quick~alfresco-acs-java-rest-api-spring-boot-starter -->
    <dependency>
      <groupId>org.alfresco</groupId>
      <artifactId>alfresco-acs-java-rest-api-spring-boot-starter</artifactId>
      <version>6.2.0</version>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
      </plugin>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>

  
<repositories>
    <repository>
      <id>alfresco-public</id>
      <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
    </repository>
  
  </repositories>

  
</project>

```

## second possibility in settings.xml


```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">

  <!-- https://maven.apache.org/guides/mini/guide-multiple-repositories.html -->
  <profiles>
    <profile>
       <id>myprofile</id>
      <repositories>
	<repository>
	  <id>alfresco-public</id>
	  <url>https://artifacts.alfresco.com/nexus/content/groups/public</url>
	</repository>
	
      </repositories>
    </profile>
  </profiles>
  

  <activeProfiles>
    <activeProfile>myprofile</activeProfile>
  </activeProfiles>
  
 
</settings>

```


# use -X to debug:

```
madon@toshibalex:~/hylandtools/sdk6/alextoken (master)$ mvn clean package -Dlicense.skip=true -e -X
Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /home/madon/alfrescosoft/apache-maven-3.8.6
Java version: 17.0.4, vendor: Eclipse Adoptium, runtime: /home/madon/alfrescobin/jdk-17.0.4+8
Default locale: en_GB, platform encoding: UTF-8
OS name: "linux", version: "6.1.0-9-amd64", arch: "amd64", family: "unix"
[DEBUG] Created new class realm maven.api
[DEBUG] Importing foreign packages into class realm maven.api
[DEBUG]   Imported: javax.annotation.* < plexus.core
[DEBUG]   Imported: javax.annotation.security.* < plexus.core
[DEBUG]   Imported: javax.inject.* < plexus.core
[DEBUG]   Imported: org.apache.maven.* < plexus.core
[DEBUG]   Imported: org.apache.maven.artifact < plexus.core
[DEBUG]   Imported: org.apache.maven.classrealm < plexus.core
[DEBUG]   Imported: org.apache.maven.cli < plexus.core
[DEBUG]   Imported: org.apache.maven.configuration < plexus.core
[DEBUG]   Imported: org.apache.maven.exception < plexus.core
[DEBUG]   Imported: org.apache.maven.execution < plexus.core
[DEBUG]   Imported: org.apache.maven.execution.scope < plexus.core
[DEBUG]   Imported: org.apache.maven.lifecycle < plexus.core
[DEBUG]   Imported: org.apache.maven.model < plexus.core
[DEBUG]   Imported: org.apache.maven.monitor < plexus.core
[DEBUG]   Imported: org.apache.maven.plugin < plexus.core
[DEBUG]   Imported: org.apache.maven.profiles < plexus.core
[DEBUG]   Imported: org.apache.maven.project < plexus.core
[DEBUG]   Imported: org.apache.maven.reporting < plexus.core
[DEBUG]   Imported: org.apache.maven.repository < plexus.core
[DEBUG]   Imported: org.apache.maven.rtinfo < plexus.core
[DEBUG]   Imported: org.apache.maven.settings < plexus.core
[DEBUG]   Imported: org.apache.maven.toolchain < plexus.core
[DEBUG]   Imported: org.apache.maven.usability < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.* < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.authentication < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.authorization < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.events < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.observers < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.proxy < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.repository < plexus.core
[DEBUG]   Imported: org.apache.maven.wagon.resource < plexus.core
[DEBUG]   Imported: org.codehaus.classworlds < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.* < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.classworlds < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.component < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.configuration < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.container < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.context < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.lifecycle < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.logging < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.personality < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.util.xml.Xpp3Dom < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.util.xml.pull.XmlPullParser < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.util.xml.pull.XmlPullParserException < plexus.core
[DEBUG]   Imported: org.codehaus.plexus.util.xml.pull.XmlSerializer < plexus.core
[DEBUG]   Imported: org.eclipse.aether.* < plexus.core
[DEBUG]   Imported: org.eclipse.aether.artifact < plexus.core
[DEBUG]   Imported: org.eclipse.aether.collection < plexus.core
[DEBUG]   Imported: org.eclipse.aether.deployment < plexus.core
[DEBUG]   Imported: org.eclipse.aether.graph < plexus.core
[DEBUG]   Imported: org.eclipse.aether.impl < plexus.core
[DEBUG]   Imported: org.eclipse.aether.installation < plexus.core
[DEBUG]   Imported: org.eclipse.aether.internal.impl < plexus.core
[DEBUG]   Imported: org.eclipse.aether.metadata < plexus.core
[DEBUG]   Imported: org.eclipse.aether.repository < plexus.core
[DEBUG]   Imported: org.eclipse.aether.resolution < plexus.core
[DEBUG]   Imported: org.eclipse.aether.spi < plexus.core
[DEBUG]   Imported: org.eclipse.aether.transfer < plexus.core
[DEBUG]   Imported: org.eclipse.aether.version < plexus.core
[DEBUG]   Imported: org.fusesource.jansi.* < plexus.core
[DEBUG]   Imported: org.slf4j.* < plexus.core
[DEBUG]   Imported: org.slf4j.event.* < plexus.core
[DEBUG]   Imported: org.slf4j.helpers.* < plexus.core
[DEBUG]   Imported: org.slf4j.spi.* < plexus.core
[DEBUG] Populating class realm maven.api
[INFO] Error stacktraces are turned on.
[DEBUG] Message scheme: color
[DEBUG] Message styles: debug info warning error success failure strong mojo project
[DEBUG] Reading global settings from /home/madon/alfrescosoft/apache-maven-3.8.6/conf/settings.xml
[DEBUG] Reading user settings from /home/madon/.m2/settings.xml
[DEBUG] Reading global toolchains from /home/madon/alfrescosoft/apache-maven-3.8.6/conf/toolchains.xml
[DEBUG] Reading user toolchains from /home/madon/.m2/toolchains.xml
[DEBUG] Using local repository at /home/madon/.m2/repository
[DEBUG] Using manager EnhancedLocalRepositoryManager with priority 10.0 for /home/madon/.m2/repository
[INFO] Scanning for projects...
[DEBUG] Extension realms for project net.madon:madon-sdk-sample:pom:1.0: (none)
[DEBUG] Looking up lifecycle mappings for packaging pom from ClassRealm[plexus.core, parent: null]
[DEBUG] Extension realms for project org.alfresco:alfresco-java-sdk-samples:pom:6.2.0: (none)
[DEBUG] Looking up lifecycle mappings for packaging pom from ClassRealm[plexus.core, parent: null]
[DEBUG] Extension realms for project org.springframework.boot:spring-boot-starter-parent:pom:3.2.4: (none)
[DEBUG] Looking up lifecycle mappings for packaging pom from ClassRealm[plexus.core, parent: null]
[DEBUG] Extension realms for project org.springframework.boot:spring-boot-dependencies:pom:3.2.4: (none)
[DEBUG] Looking up lifecycle mappings for packaging pom from ClassRealm[plexus.core, parent: null]
[DEBUG] === REACTOR BUILD PLAN ================================================
[DEBUG] Project: net.madon:madon-sdk-sample:pom:1.0
[DEBUG] Tasks:   [clean, package]
[DEBUG] Style:   Regular
[DEBUG] =======================================================================
[INFO] 
[INFO] ---------------------< net.madon:madon-sdk-sample >---------------------
[INFO] Building Madon :: Java SDK :: Samples 1.0
[INFO] --------------------------------[ pom ]---------------------------------
[DEBUG] Lifecycle default -> [validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy]
[DEBUG] Lifecycle clean -> [pre-clean, clean, post-clean]
[DEBUG] Lifecycle site -> [pre-site, site, post-site, site-deploy]
[DEBUG] Lifecycle default -> [validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy]
[DEBUG] Lifecycle clean -> [pre-clean, clean, post-clean]
[DEBUG] Lifecycle site -> [pre-site, site, post-site, site-deploy]
[DEBUG] Lifecycle default -> [validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy]
[DEBUG] Lifecycle clean -> [pre-clean, clean, post-clean]
[DEBUG] Lifecycle site -> [pre-site, site, post-site, site-deploy]
[DEBUG] Lifecycle default -> [validate, initialize, generate-sources, process-sources, generate-resources, process-resources, compile, process-classes, generate-test-sources, process-test-sources, generate-test-resources, process-test-resources, test-compile, process-test-classes, test, prepare-package, package, pre-integration-test, integration-test, post-integration-test, verify, install, deploy]
[DEBUG] Lifecycle clean -> [pre-clean, clean, post-clean]
[DEBUG] Lifecycle site -> [pre-site, site, post-site, site-deploy]
[DEBUG] === PROJECT BUILD PLAN ================================================
[DEBUG] Project:       net.madon:madon-sdk-sample:1.0
[DEBUG] Dependencies (collect): [compile+runtime]
[DEBUG] Dependencies (resolve): [compile+runtime]
[DEBUG] Repositories (dependencies): [alfresco-public (https://artifacts.alfresco.com/nexus/content/groups/public, default, releases+snapshots), central (https://repo.maven.apache.org/maven2, default, releases)]
[DEBUG] Repositories (plugins)     : [central (https://repo.maven.apache.org/maven2, default, releases)]
[DEBUG] -----------------------------------------------------------------------
[DEBUG] Goal:          org.apache.maven.plugins:maven-clean-plugin:3.3.2:clean (default-clean)
[DEBUG] Style:         Regular
[DEBUG] Configuration: <?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <directory default-value="${project.build.directory}"/>
  <excludeDefaultDirectories default-value="false">${maven.clean.excludeDefaultDirectories}</excludeDefaultDirectories>
  <failOnError default-value="true">${maven.clean.failOnError}</failOnError>
  <fast default-value="false">${maven.clean.fast}</fast>
  <fastDir>${maven.clean.fastDir}</fastDir>
  <fastMode default-value="background">${maven.clean.fastMode}</fastMode>
  <followSymLinks default-value="false">${maven.clean.followSymLinks}</followSymLinks>
  <outputDirectory default-value="${project.build.outputDirectory}"/>
  <reportDirectory default-value="${project.build.outputDirectory}"/>
  <retryOnError default-value="true">${maven.clean.retryOnError}</retryOnError>
  <session default-value="${session}"/>
  <skip default-value="false">${maven.clean.skip}</skip>
  <testOutputDirectory default-value="${project.build.testOutputDirectory}"/>
  <verbose>${maven.clean.verbose}</verbose>
</configuration>
[DEBUG] -----------------------------------------------------------------------
[DEBUG] Goal:          org.springframework.boot:spring-boot-maven-plugin:3.2.4:repackage (repackage)
[DEBUG] Style:         Regular
[DEBUG] Configuration: <?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <attach default-value="true"/>
  <excludeDevtools default-value="true">${spring-boot.repackage.excludeDevtools}</excludeDevtools>
  <excludeDockerCompose default-value="true">${spring-boot.repackage.excludeDockerCompose}</excludeDockerCompose>
  <excludeGroupIds default-value="">${spring-boot.excludeGroupIds}</excludeGroupIds>
  <excludes>${spring-boot.excludes}</excludes>
  <executable default-value="false"/>
  <finalName default-value="${project.build.finalName}"/>
  <includeSystemScope default-value="false"/>
  <includes>${spring-boot.includes}</includes>
  <layout>${spring-boot.repackage.layout}</layout>
  <mainClass>${start-class}</mainClass>
  <outputDirectory default-value="${project.build.directory}"/>
  <outputTimestamp default-value="${project.build.outputTimestamp}"/>
  <project default-value="${project}"/>
  <session default-value="${session}"/>
  <skip default-value="false">${spring-boot.repackage.skip}</skip>
</configuration>
[DEBUG] =======================================================================
[DEBUG] Using mirror maven-default-http-blocker (http://0.0.0.0/) for apache.snapshots (http://repository.apache.org/snapshots).
[DEBUG] Using mirror maven-default-http-blocker (http://0.0.0.0/) for ow2-snapshot (http://repository.ow2.org/nexus/content/repositories/snapshots).
[DEBUG] Dependency collection stats {ConflictMarker.analyzeTime=1276690, ConflictMarker.markTime=669180, ConflictMarker.nodeCount=240, ConflictIdSorter.graphTime=1041616, ConflictIdSorter.topsortTime=590463, ConflictIdSorter.conflictIdCount=90, ConflictIdSorter.conflictIdCycleCount=0, ConflictResolver.totalTime=11751528, ConflictResolver.conflictItemCount=200, DefaultDependencyCollector.collectTime=568233439, DefaultDependencyCollector.transformTime=16990951}
[DEBUG] net.madon:madon-sdk-sample:pom:1.0
[DEBUG]    org.alfresco:alfresco-java-event-api-spring-boot-starter:jar:6.2.0:compile
[DEBUG]       org.alfresco:alfresco-java-event-api-spring-boot:jar:6.2.0:compile
[DEBUG]          org.springframework.boot:spring-boot:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]             org.springframework:spring-core:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework:spring-jcl:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]             org.springframework:spring-context:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework:spring-aop:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework:spring-beans:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework:spring-expression:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                io.micrometer:micrometer-observation:jar:1.12.4:compile (version managed from 1.12.4)
[DEBUG]                   io.micrometer:micrometer-commons:jar:1.12.4:compile (version managed from 1.12.4)
[DEBUG]          org.springframework.boot:spring-boot-autoconfigure:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]          org.springframework.boot:spring-boot-starter-integration:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]             org.springframework.boot:spring-boot-starter-aop:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]                org.aspectj:aspectjweaver:jar:1.9.21:compile (version managed from 1.9.21)
[DEBUG]             org.springframework.integration:spring-integration-core:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]                org.springframework:spring-messaging:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework:spring-tx:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]                org.springframework.retry:spring-retry:jar:2.0.5:compile (version managed from 2.0.5)
[DEBUG]                io.projectreactor:reactor-core:jar:3.6.4:compile (version managed from 3.6.4)
[DEBUG]                   org.reactivestreams:reactive-streams:jar:1.0.4:compile (version managed from 1.0.4)
[DEBUG]          org.springframework.boot:spring-boot-starter-activemq:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]             org.springframework.boot:spring-boot-starter:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]                org.springframework.boot:spring-boot-starter-logging:jar:3.2.4:compile (version managed from 3.2.4)
[DEBUG]                   ch.qos.logback:logback-classic:jar:1.4.14:compile (version managed from 1.4.14)
[DEBUG]                      ch.qos.logback:logback-core:jar:1.4.14:compile (version managed from 1.4.14)
[DEBUG]                   org.apache.logging.log4j:log4j-to-slf4j:jar:2.21.1:compile (version managed from 2.21.1)
[DEBUG]                      org.apache.logging.log4j:log4j-api:jar:2.21.1:compile (version managed from 2.21.1)
[DEBUG]                   org.slf4j:jul-to-slf4j:jar:2.0.12:compile (version managed from 2.0.12)
[DEBUG]                jakarta.annotation:jakarta.annotation-api:jar:2.1.1:compile (version managed from 2.1.1)
[DEBUG]                org.yaml:snakeyaml:jar:2.2:compile (version managed from 2.2)
[DEBUG]             org.springframework:spring-jms:jar:6.1.5:compile (version managed from 6.1.5)
[DEBUG]             org.apache.activemq:activemq-client-jakarta:jar:5.18.3:compile (version managed from 5.18.3)
[DEBUG]                jakarta.jms:jakarta.jms-api:jar:3.1.0:compile (version managed from 3.1.0)
[DEBUG]                org.fusesource.hawtbuf:hawtbuf:jar:1.11:compile
[DEBUG]          org.springframework.integration:spring-integration-jms:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]          org.alfresco:alfresco-java-event-api-integration:jar:6.2.0:compile
[DEBUG]             com.fasterxml.jackson.core:jackson-databind:jar:2.15.4:compile (version managed from 2.15.4)
[DEBUG]                com.fasterxml.jackson.core:jackson-annotations:jar:2.15.4:compile (version managed from 2.15.4)
[DEBUG]                com.fasterxml.jackson.core:jackson-core:jar:2.15.4:compile (version managed from 2.15.4)
[DEBUG]             org.slf4j:slf4j-api:jar:2.0.12:compile (version managed from 2.0.12)
[DEBUG]          org.alfresco:alfresco-java-event-api-handling:jar:6.2.0:compile
[DEBUG]          org.alfresco:acs-event-model:jar:0.0.25:compile
[DEBUG]    org.alfresco:alfresco-acs-java-rest-api-spring-boot-starter:jar:6.2.0:compile
[DEBUG]       org.alfresco:alfresco-acs-java-rest-api-spring-boot:jar:6.2.0:compile
[DEBUG]          io.github.openfeign:feign-httpclient:jar:13.1:compile
[DEBUG]             io.github.openfeign:feign-core:jar:13.1:compile
[DEBUG]             org.apache.httpcomponents:httpclient:jar:4.5.14:compile
[DEBUG]                org.apache.httpcomponents:httpcore:jar:4.4.16:compile (version managed from 4.4.16)
[DEBUG]                commons-logging:commons-logging:jar:1.2:compile
[DEBUG]                commons-codec:commons-codec:jar:1.16.1:compile (version managed from 1.11)
[DEBUG]          org.alfresco:alfresco-core-rest-api:jar:6.2.0:compile
[DEBUG]             io.swagger:swagger-annotations:jar:1.5.20:compile
[DEBUG]             org.springframework.cloud:spring-cloud-starter-openfeign:jar:4.1.0:compile
[DEBUG]                org.springframework.cloud:spring-cloud-starter:jar:4.1.0:compile
[DEBUG]                   org.springframework.cloud:spring-cloud-context:jar:4.1.0:compile
[DEBUG]                   org.springframework.security:spring-security-rsa:jar:1.1.1:compile
[DEBUG]                      org.bouncycastle:bcprov-jdk18on:jar:1.74:compile
[DEBUG]                org.springframework.cloud:spring-cloud-openfeign-core:jar:4.1.0:compile
[DEBUG]                   io.github.openfeign.form:feign-form-spring:jar:3.8.0:compile
[DEBUG]                      io.github.openfeign.form:feign-form:jar:3.8.0:compile
[DEBUG]                   commons-fileupload:commons-fileupload:jar:1.5:compile
[DEBUG]                      commons-io:commons-io:jar:2.11.0:compile
[DEBUG]                org.springframework:spring-web:jar:6.1.5:compile (version managed from 6.1.1)
[DEBUG]                org.springframework.cloud:spring-cloud-commons:jar:4.1.0:compile
[DEBUG]                   org.springframework.security:spring-security-crypto:jar:6.2.3:compile (version managed from 6.2.0)
[DEBUG]                io.github.openfeign:feign-slf4j:jar:13.1:compile
[DEBUG]             org.alfresco:alfresco-java-rest-api-common:jar:6.2.0:compile
[DEBUG]                org.springframework.security:spring-security-oauth2-client:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]                   org.springframework.security:spring-security-core:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]                   org.springframework.security:spring-security-oauth2-core:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]                   org.springframework.security:spring-security-web:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]                   com.nimbusds:oauth2-oidc-sdk:jar:9.43.3:compile
[DEBUG]                      com.github.stephenc.jcip:jcip-annotations:jar:1.0-1:compile
[DEBUG]                      com.nimbusds:content-type:jar:2.2:compile
[DEBUG]                      net.minidev:json-smart:jar:2.5.0:compile (version managed from [1.3.3,2.4.10])
[DEBUG]                         net.minidev:accessors-smart:jar:2.5.0:compile
[DEBUG]                            org.ow2.asm:asm:jar:9.3:compile
[DEBUG]                      com.nimbusds:lang-tag:jar:1.7:compile
[DEBUG]                      com.nimbusds:nimbus-jose-jwt:jar:9.24.4:compile
[DEBUG]                org.springframework.security:spring-security-config:jar:6.2.3:compile (version managed from 6.2.3)
[DEBUG]             com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.15.4:compile (version managed from 2.15.4)
[DEBUG]          org.alfresco:alfresco-auth-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-discovery-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-governance-core-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-governance-classification-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-model-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-search-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-search-sql-rest-api:jar:6.2.0:compile
[DEBUG]          org.alfresco:alfresco-event-gateway-api-client:jar:6.2.0:compile
[INFO] 
[INFO] --- maven-clean-plugin:3.3.2:clean (default-clean) @ madon-sdk-sample ---
[DEBUG] Dependency collection stats {ConflictMarker.analyzeTime=78167, ConflictMarker.markTime=100612, ConflictMarker.nodeCount=2, ConflictIdSorter.graphTime=58219, ConflictIdSorter.topsortTime=14019, ConflictIdSorter.conflictIdCount=2, ConflictIdSorter.conflictIdCycleCount=0, ConflictResolver.totalTime=101431, ConflictResolver.conflictItemCount=2, DefaultDependencyCollector.collectTime=8321209, DefaultDependencyCollector.transformTime=420762}
[DEBUG] org.apache.maven.plugins:maven-clean-plugin:jar:3.3.2
[DEBUG]    org.codehaus.plexus:plexus-utils:jar:4.0.0:compile
[DEBUG] Created new class realm plugin>org.apache.maven.plugins:maven-clean-plugin:3.3.2
[DEBUG] Importing foreign packages into class realm plugin>org.apache.maven.plugins:maven-clean-plugin:3.3.2
[DEBUG]   Imported:  < maven.api
[DEBUG] Populating class realm plugin>org.apache.maven.plugins:maven-clean-plugin:3.3.2
[DEBUG]   Included: org.apache.maven.plugins:maven-clean-plugin:jar:3.3.2
[DEBUG]   Included: org.codehaus.plexus:plexus-utils:jar:4.0.0
[DEBUG] Configuring mojo org.apache.maven.plugins:maven-clean-plugin:3.3.2:clean from plugin realm ClassRealm[plugin>org.apache.maven.plugins:maven-clean-plugin:3.3.2, parent: jdk.internal.loader.ClassLoaders$AppClassLoader@5cb0d902]
[DEBUG] Configuring mojo 'org.apache.maven.plugins:maven-clean-plugin:3.3.2:clean' with basic configurator -->
[DEBUG]   (f) directory = /home/madon/hylandtools/sdk6/alextoken/target
[DEBUG]   (f) excludeDefaultDirectories = false
[DEBUG]   (f) failOnError = true
[DEBUG]   (f) fast = false
[DEBUG]   (f) fastMode = background
[DEBUG]   (f) followSymLinks = false
[DEBUG]   (f) outputDirectory = /home/madon/hylandtools/sdk6/alextoken/target/classes
[DEBUG]   (f) reportDirectory = /home/madon/hylandtools/sdk6/alextoken/target/classes
[DEBUG]   (f) retryOnError = true
[DEBUG]   (f) session = org.apache.maven.execution.MavenSession@3c0fbd3a
[DEBUG]   (f) skip = false
[DEBUG]   (f) testOutputDirectory = /home/madon/hylandtools/sdk6/alextoken/target/test-classes
[DEBUG] -- end configuration --
[DEBUG] Skipping non-existing directory /home/madon/hylandtools/sdk6/alextoken/target
[DEBUG] Skipping non-existing directory /home/madon/hylandtools/sdk6/alextoken/target/classes
[DEBUG] Skipping non-existing directory /home/madon/hylandtools/sdk6/alextoken/target/test-classes
[DEBUG] Skipping non-existing directory /home/madon/hylandtools/sdk6/alextoken/target/classes
[INFO] 
[INFO] --- spring-boot-maven-plugin:3.2.4:repackage (repackage) @ madon-sdk-sample ---
[DEBUG] Dependency collection stats {ConflictMarker.analyzeTime=92929, ConflictMarker.markTime=74081, ConflictMarker.nodeCount=57, ConflictIdSorter.graphTime=65295, ConflictIdSorter.topsortTime=51849, ConflictIdSorter.conflictIdCount=39, ConflictIdSorter.conflictIdCycleCount=0, ConflictResolver.totalTime=1375490, ConflictResolver.conflictItemCount=56, DefaultDependencyCollector.collectTime=125266150, DefaultDependencyCollector.transformTime=1711249}
[DEBUG] org.springframework.boot:spring-boot-maven-plugin:jar:3.2.4
[DEBUG]    org.springframework.boot:spring-boot-buildpack-platform:jar:3.2.4:runtime
[DEBUG]       com.fasterxml.jackson.core:jackson-databind:jar:2.14.2:runtime
[DEBUG]          com.fasterxml.jackson.core:jackson-annotations:jar:2.14.2:runtime
[DEBUG]          com.fasterxml.jackson.core:jackson-core:jar:2.14.2:runtime
[DEBUG]       com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.14.2:runtime
[DEBUG]       net.java.dev.jna:jna-platform:jar:5.13.0:runtime
[DEBUG]          net.java.dev.jna:jna:jar:5.13.0:runtime
[DEBUG]       org.apache.commons:commons-compress:jar:1.21:runtime
[DEBUG]       org.apache.httpcomponents.client5:httpclient5:jar:5.2.3:runtime
[DEBUG]          org.apache.httpcomponents.core5:httpcore5:jar:5.2.4:runtime
[DEBUG]          org.apache.httpcomponents.core5:httpcore5-h2:jar:5.2.4:runtime
[DEBUG]       org.tomlj:tomlj:jar:1.0.0:runtime
[DEBUG]          org.antlr:antlr4-runtime:jar:4.7.2:runtime
[DEBUG]          com.google.code.findbugs:jsr305:jar:3.0.2:runtime
[DEBUG]    org.springframework.boot:spring-boot-loader-tools:jar:3.2.4:runtime
[DEBUG]    org.apache.maven.shared:maven-common-artifact-filters:jar:3.3.2:runtime
[DEBUG]       org.slf4j:slf4j-api:jar:1.7.36:compile
[DEBUG]    org.springframework:spring-core:jar:6.1.5:runtime
[DEBUG]       org.springframework:spring-jcl:jar:6.1.5:runtime
[DEBUG]    org.springframework:spring-context:jar:6.1.5:runtime
[DEBUG]       org.springframework:spring-aop:jar:6.1.5:runtime
[DEBUG]       org.springframework:spring-beans:jar:6.1.5:runtime
[DEBUG]       org.springframework:spring-expression:jar:6.1.5:runtime
[DEBUG]       io.micrometer:micrometer-observation:jar:1.12.4:runtime
[DEBUG]          io.micrometer:micrometer-commons:jar:1.12.4:runtime
[DEBUG]    org.sonatype.plexus:plexus-build-api:jar:0.0.7:runtime
[DEBUG]       org.codehaus.plexus:plexus-utils:jar:1.5.8:compile
[DEBUG]    org.apache.maven.plugins:maven-shade-plugin:jar:3.5.0:compile (optional)
[DEBUG]       org.ow2.asm:asm:jar:9.5:compile (optional)
[DEBUG]       org.ow2.asm:asm-commons:jar:9.5:compile (optional)
[DEBUG]          org.ow2.asm:asm-tree:jar:9.5:compile (optional)
[DEBUG]       org.jdom:jdom2:jar:2.0.6.1:compile (optional)
[DEBUG]       org.apache.maven.shared:maven-dependency-tree:jar:3.2.1:compile (optional)
[DEBUG]          org.eclipse.aether:aether-util:jar:1.0.0.v20140518:compile (optional)
[DEBUG]             org.eclipse.aether:aether-api:jar:1.0.0.v20140518:compile (optional)
[DEBUG]       commons-io:commons-io:jar:2.13.0:compile (optional)
[DEBUG]       org.vafer:jdependency:jar:2.8.0:compile (optional)
[DEBUG]       org.apache.commons:commons-collections4:jar:4.4:compile (optional)
[DEBUG] Created new class realm plugin>org.springframework.boot:spring-boot-maven-plugin:3.2.4
[DEBUG] Importing foreign packages into class realm plugin>org.springframework.boot:spring-boot-maven-plugin:3.2.4
[DEBUG]   Imported:  < maven.api
[DEBUG] Populating class realm plugin>org.springframework.boot:spring-boot-maven-plugin:3.2.4
[DEBUG]   Included: org.springframework.boot:spring-boot-maven-plugin:jar:3.2.4
[DEBUG]   Included: org.springframework.boot:spring-boot-buildpack-platform:jar:3.2.4
[DEBUG]   Included: com.fasterxml.jackson.core:jackson-databind:jar:2.14.2
[DEBUG]   Included: com.fasterxml.jackson.core:jackson-annotations:jar:2.14.2
[DEBUG]   Included: com.fasterxml.jackson.core:jackson-core:jar:2.14.2
[DEBUG]   Included: com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.14.2
[DEBUG]   Included: net.java.dev.jna:jna-platform:jar:5.13.0
[DEBUG]   Included: net.java.dev.jna:jna:jar:5.13.0
[DEBUG]   Included: org.apache.commons:commons-compress:jar:1.21
[DEBUG]   Included: org.apache.httpcomponents.client5:httpclient5:jar:5.2.3
[DEBUG]   Included: org.apache.httpcomponents.core5:httpcore5:jar:5.2.4
[DEBUG]   Included: org.apache.httpcomponents.core5:httpcore5-h2:jar:5.2.4
[DEBUG]   Included: org.tomlj:tomlj:jar:1.0.0
[DEBUG]   Included: org.antlr:antlr4-runtime:jar:4.7.2
[DEBUG]   Included: com.google.code.findbugs:jsr305:jar:3.0.2
[DEBUG]   Included: org.springframework.boot:spring-boot-loader-tools:jar:3.2.4
[DEBUG]   Included: org.apache.maven.shared:maven-common-artifact-filters:jar:3.3.2
[DEBUG]   Included: org.springframework:spring-core:jar:6.1.5
[DEBUG]   Included: org.springframework:spring-jcl:jar:6.1.5
[DEBUG]   Included: org.springframework:spring-context:jar:6.1.5
[DEBUG]   Included: org.springframework:spring-aop:jar:6.1.5
[DEBUG]   Included: org.springframework:spring-beans:jar:6.1.5
[DEBUG]   Included: org.springframework:spring-expression:jar:6.1.5
[DEBUG]   Included: io.micrometer:micrometer-observation:jar:1.12.4
[DEBUG]   Included: io.micrometer:micrometer-commons:jar:1.12.4
[DEBUG]   Included: org.sonatype.plexus:plexus-build-api:jar:0.0.7
[DEBUG]   Included: org.codehaus.plexus:plexus-utils:jar:1.5.8
[DEBUG]   Included: org.apache.maven.plugins:maven-shade-plugin:jar:3.5.0
[DEBUG]   Included: org.ow2.asm:asm:jar:9.5
[DEBUG]   Included: org.ow2.asm:asm-commons:jar:9.5
[DEBUG]   Included: org.ow2.asm:asm-tree:jar:9.5
[DEBUG]   Included: org.jdom:jdom2:jar:2.0.6.1
[DEBUG]   Included: org.apache.maven.shared:maven-dependency-tree:jar:3.2.1
[DEBUG]   Included: org.eclipse.aether:aether-util:jar:1.0.0.v20140518
[DEBUG]   Included: commons-io:commons-io:jar:2.13.0
[DEBUG]   Included: org.vafer:jdependency:jar:2.8.0
[DEBUG]   Included: org.apache.commons:commons-collections4:jar:4.4
[DEBUG] Configuring mojo org.springframework.boot:spring-boot-maven-plugin:3.2.4:repackage from plugin realm ClassRealm[plugin>org.springframework.boot:spring-boot-maven-plugin:3.2.4, parent: jdk.internal.loader.ClassLoaders$AppClassLoader@5cb0d902]
[DEBUG] Configuring mojo 'org.springframework.boot:spring-boot-maven-plugin:3.2.4:repackage' with basic configurator -->
[DEBUG]   (f) attach = true
[DEBUG]   (f) excludeDevtools = true
[DEBUG]   (f) excludeDockerCompose = true
[DEBUG]   (f) excludes = []
[DEBUG]   (f) executable = false
[DEBUG]   (f) finalName = madon-sdk-sample-1.0
[DEBUG]   (f) includeSystemScope = false
[DEBUG]   (f) includes = []
[DEBUG]   (f) outputDirectory = /home/madon/hylandtools/sdk6/alextoken/target
[DEBUG]   (f) project = MavenProject: net.madon:madon-sdk-sample:1.0 @ /home/madon/hylandtools/sdk6/alextoken/pom.xml
[DEBUG]   (f) session = org.apache.maven.execution.MavenSession@3c0fbd3a
[DEBUG]   (f) skip = false
[DEBUG] -- end configuration --
[DEBUG] repackage goal could not be applied to pom project.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.300 s
[INFO] Finished at: 2024-07-11T11:00:59+02:00
[INFO] ------------------------------------------------------------------------
```


# tree structure

https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html



# [WARNING] The goal is skip due to packaging 'pom'



change:

<packaging>pom</packaging>


into

<packaging>jar</packaging>




# missing priperties file


```
madon@toshibalex:~/hylandtools/sdk6/alextoken (master)$ java -jar /home/madon/hylandtools/sdk6/alextoken/target/sdk-alex-6.2.0.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.4)

2024-07-11T11:38:08.438+02:00  INFO 35272 --- [           main] net.madon.tutorial.SdkDemoApplication    : Starting SdkDemoApplication v6.2.0 using Java 17.0.7 with PID 35272 (/home/madon/hylandtools/sdk6/alextoken/target/sdk-alex-6.2.0.jar started by madon in /home/madon/hylandtools/sdk6/alextoken)
2024-07-11T11:38:08.446+02:00  INFO 35272 --- [           main] net.madon.tutorial.SdkDemoApplication    : No active profile set, falling back to 1 default profile: "default"
2024-07-11T11:38:09.903+02:00  INFO 35272 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=1bd049a9-7c05-347f-887f-3b905d1ede4d
2024-07-11T11:38:09.909+02:00  WARN 35272 --- [           main] s.c.a.AnnotationConfigApplicationContext : Exception encountered during context initialization - cancelling refresh attempt: org.springframework.beans.factory.BeanDefinitionStoreException: Invalid bean definition with name 'org.alfresco.governance.classification.handler.ClassificationReasonsApiClient' defined in null: Could not resolve placeholder 'content.service.url' in value "http://${content.service.url}"
2024-07-11T11:38:09.951+02:00  INFO 35272 --- [           main] .s.b.a.l.ConditionEvaluationReportLogger : 

Error starting ApplicationContext. To display the condition evaluation report re-run your application with 'debug' enabled.
2024-07-11T11:38:09.984+02:00 ERROR 35272 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanDefinitionStoreException: Invalid bean definition with name 'org.alfresco.governance.classification.handler.ClassificationReasonsApiClient' defined in null: Could not resolve placeholder 'content.service.url' in value "http://${content.service.url}"
	at org.springframework.beans.factory.config.PlaceholderConfigurerSupport.doProcessProperties(PlaceholderConfigurerSupport.java:230) ~[spring-beans-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.PropertySourcesPlaceholderConfigurer.processProperties(PropertySourcesPlaceholderConfigurer.java:207) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.PropertySourcesPlaceholderConfigurer.postProcessBeanFactory(PropertySourcesPlaceholderConfigurer.java:173) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:363) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:189) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:788) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:606) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:754) ~[spring-boot-3.2.4.jar!/:3.2.4]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:456) ~[spring-boot-3.2.4.jar!/:3.2.4]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:334) ~[spring-boot-3.2.4.jar!/:3.2.4]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1354) ~[spring-boot-3.2.4.jar!/:3.2.4]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1343) ~[spring-boot-3.2.4.jar!/:3.2.4]
	at net.madon.tutorial.SdkDemoApplication.main(SdkDemoApplication.java:27) ~[!/:6.2.0]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
	at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:91) ~[sdk-alex-6.2.0.jar:6.2.0]
	at org.springframework.boot.loader.launch.Launcher.launch(Launcher.java:53) ~[sdk-alex-6.2.0.jar:6.2.0]
	at org.springframework.boot.loader.launch.JarLauncher.main(JarLauncher.java:58) ~[sdk-alex-6.2.0.jar:6.2.0]
Caused by: java.lang.IllegalArgumentException: Could not resolve placeholder 'content.service.url' in value "http://${content.service.url}"
	at org.springframework.util.PropertyPlaceholderHelper.parseStringValue(PropertyPlaceholderHelper.java:180) ~[spring-core-6.1.5.jar!/:6.1.5]
	at org.springframework.util.PropertyPlaceholderHelper.replacePlaceholders(PropertyPlaceholderHelper.java:126) ~[spring-core-6.1.5.jar!/:6.1.5]
	at org.springframework.core.env.AbstractPropertyResolver.doResolvePlaceholders(AbstractPropertyResolver.java:239) ~[spring-core-6.1.5.jar!/:6.1.5]
	at org.springframework.core.env.AbstractPropertyResolver.resolveRequiredPlaceholders(AbstractPropertyResolver.java:210) ~[spring-core-6.1.5.jar!/:6.1.5]
	at org.springframework.context.support.PropertySourcesPlaceholderConfigurer.lambda$processProperties$0(PropertySourcesPlaceholderConfigurer.java:200) ~[spring-context-6.1.5.jar!/:6.1.5]
	at org.springframework.beans.factory.config.BeanDefinitionVisitor.resolveStringValue(BeanDefinitionVisitor.java:293) ~[spring-beans-6.1.5.jar!/:6.1.5]
	at org.springframework.beans.factory.config.BeanDefinitionVisitor.resolveValue(BeanDefinitionVisitor.java:219) ~[spring-beans-6.1.5.jar!/:6.1.5]
	at org.springframework.beans.factory.config.BeanDefinitionVisitor.visitPropertyValues(BeanDefinitionVisitor.java:147) ~[spring-beans-6.1.5.jar!/:6.1.5]
	at org.springframework.beans.factory.config.BeanDefinitionVisitor.visitBeanDefinition(BeanDefinitionVisitor.java:85) ~[spring-beans-6.1.5.jar!/:6.1.5]
	at org.springframework.beans.factory.config.PlaceholderConfigurerSupport.doProcessProperties(PlaceholderConfigurerSupport.java:227) ~[spring-beans-6.1.5.jar!/:6.1.5]
	... 19 common frames omitted

```
