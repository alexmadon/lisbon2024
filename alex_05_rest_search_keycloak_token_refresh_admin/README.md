# compile and run

```
cd /home/madon/hylandtools/sdk6/alex_rest_search_keycloak_token
mvn clean package -Dlicense.skip=true 
java -jar /home/madon/hylandtools/sdk6/alex_05_rest_search_keycloak_token_refresh_admin/target/sdk-alex-0.0.1-SNAPSHOT.jar
/home/madon/alfrescobin/jdk-17.0.4+8/bin/java  -jar /home/madon/hylandtools/sdk6/alex_05_rest_search_keycloak_token_refresh_admin/target/sdk-alex-0.0.1-SNAPSHOT.jar
```


# run with spring DEBUG or TRACE if necessary

```
 java -jar /home/madon/hylandtools/sdk6/alex_03_rest_search_keycloak_token/target/sdk-alex-0.0.1-SNAPSHOT.jar
 java -Dlogging.level.root=DEBUG -jar /home/madon/hylandtools/sdk6/alex_03_rest_search_keycloak_token/target/sdk-alex-0.0.1-SNAPSHOT.jar
 java -Dlogging.level.root=TRACE -jar /home/madon/hylandtools/sdk6/alex_03_rest_search_keycloak_token/target/sdk-alex-0.0.1-SNAPSHOT.jar
```


# default logs

```
2024-07-25T08:58:44,086 [] DEBUG [authentication.identityservice.IdentityServiceRemoteUserMapper] [http-nio-127.0.0.1-8080-exec-4] Trying bearer token...
2024-07-25T08:58:44,093 [] DEBUG [authentication.identityservice.SpringBasedIdentityServiceFacade] [http-nio-127.0.0.1-8080-exec-4] Bearer token outcome: {sub=e5b3a5d8-12dd-4d21-a8f2-1ef4bd42225d, resource_access={account={roles=[manage-account, manage-account-links, view-profile]}}, email_verified=true, clientHost=127.0.0.1, allowed-origins=[*], iss=http://127.0.0.2:8180/auth/realms/alfresco, typ=Bearer, preferred_username=service-account-alfresco-sdk, given_name=, clientAddress=127.0.0.1, client_id=alfresco-sdk, aud=[account], realm_access={roles=[default-roles-alfresco, offline_access, admin, test_role, uma_authorization, user]}, azp=alfresco-sdk, scope=openid email profile, exp=2024-07-25T07:03:43Z, iat=2024-07-25T06:58:43Z, family_name=, jti=40921ec4-69bb-40e5-89be-9b3f41768aeb, email=service-account-alfresco-sdk@madon.net}
2024-07-25T08:58:44,094 [] DEBUG [authentication.identityservice.IdentityServiceRemoteUserMapper] [http-nio-127.0.0.1-8080-exec-4] Trying bearer token...
2024-07-25T08:58:44,096 [] DEBUG [authentication.identityservice.SpringBasedIdentityServiceFacade] [http-nio-127.0.0.1-8080-exec-4] Bearer token outcome: {sub=e5b3a5d8-12dd-4d21-a8f2-1ef4bd42225d, resource_access={account={roles=[manage-account, manage-account-links, view-profile]}}, email_verified=true, clientHost=127.0.0.1, allowed-origins=[*], iss=http://127.0.0.2:8180/auth/realms/alfresco, typ=Bearer, preferred_username=service-account-alfresco-sdk, given_name=, clientAddress=127.0.0.1, client_id=alfresco-sdk, aud=[account], realm_access={roles=[default-roles-alfresco, offline_access, admin, test_role, uma_authorization, user]}, azp=alfresco-sdk, scope=openid email profile, exp=2024-07-25T07:03:43Z, iat=2024-07-25T06:58:43Z, family_name=, jti=40921ec4-69bb-40e5-89be-9b3f41768aeb, email=service-account-alfresco-sdk@madon.net}
```

# keycloak session lifetime

C'est simple à reproduire, il suffit de définir une durée de session très courte coté Keycloak (1 minute par exemple).


https://stackoverflow.com/questions/75776236/keycloak-session-and-token-timeouts

In my realm settings, under "Access Token Lifespan" I have 5 minutes. In the Sessions tab, the SSO Session Idle is set to 14 days. In the same tab, the SSO Session Max is set to 9999 days.


Offline Session 

http://127.0.0.2:8180/auth/admin/master/console/#/alfresco/realm-settings/sessions

http://127.0.0.2:8180/auth/admin/master/console/#/alfresco/realm-settings/tokens





https://stackoverflow.com/questions/51386337/refresh-access-token-via-refresh-token-in-keycloak

keycloak has REST API for creating an `access_token` using `refresh_token`. It is a POST endpoint with application/x-www-form-urlencoded

Here is how it looks:

```
Method: POST
URL: https://keycloak.example.com/auth/realms/myrealm/protocol/openid-connect/token
Body type: x-www-form-urlencoded
Form fields:    
client_id : <my-client-name>
grant_type : refresh_token
refresh_token: <my-refresh-token>
```


```
madon@toshibalex:~/airbus/charles_token_expiration_01765669/bug3 $ rg refresh
realm-export.json
7:  "refreshTokenMaxReuse": 0,
```

can catch only traffic to keycloak:



```
tcpdump -s0 -i lo host 127.0.0.2 -w dumpkk3.pcap
```



```
madon@toshibalex:~/airbus/charles_token_expiration_01765669/bug3 $ rg -i token
realm-export.json
6:  "revokeRefreshToken": false,
7:  "refreshTokenMaxReuse": 0,
8:  "accessTokenLifespan": 300,
9:  "accessTokenLifespanForImplicitFlow": 900,
24:  "actionTokenGeneratedByAdminLifespan": 43200,
```


```
madon@toshibalex:~/airbus/charles_token_expiration_01765669/bug3 $ rg -i session
realm-export.json
10:  "ssoSessionIdleTimeout": 1800,
11:  "ssoSessionMaxLifespan": 36000,
12:  "ssoSessionIdleTimeoutRememberMe": 0,
13:  "ssoSessionMaxLifespanRememberMe": 0,
14:  "offlineSessionIdleTimeout": 2592000,
15:  "offlineSessionMaxLifespanEnabled": false,
16:  "offlineSessionMaxLifespan": 5184000,
17:  "clientSessionIdleTimeout": 0,
18:  "clientSessionMaxLifespan": 0,
19:  "clientOfflineSessionIdleTimeout": 0,
20:  "clientOfflineSessionMaxLifespan": 0,
```



https://developer.okta.com/docs/guides/refresh-tokens/main/
To refresh your access token and an ID token, you send a token request with a grant_type of `refresh_token` . Be sure to include the openid scope when you want to


http://127.0.0.2:8180/auth/admin/master/console/#/alfresco/realm-settings/tokens
Access Token Lifespan
change from 5 minutes
to 5 seconds
200OK

https://stackoverflow.com/questions/52040265/how-to-specify-refresh-tokens-lifespan-in-keycloak
TL;DR One can infer that the refresh token lifespan will be equal to the smallest value among (SSO Session Idle, Client Session Idle, SSO Session Max, and Client Session Max).

SSO Session Settings
SSO Session Idle
change from 30 minutes to 6 seconds

