# Java micro-services

## Build
build all projects
```
./gradlew clean build
```
build all projects without tests
```
./gradlew clean build -x test
```

## Running project
respect the following order

### Redis
```
docker pull redis:4.0.8-alpine
docker run --name redis -p 6379:6379 redis:4.0.8-alpine
```
### config-server
update your local path: in `config-server/src/main/resources`

`cloud.config.server.native.search-locations: file:///${user.home}/{pathToGitRepo}/cs-games-2018-relay-cloud/config-server/config`
```
./gradlew :config-server:bootRun -Dspring.profiles.active=native
```
to test it worked:  
`GET` url: `http://configUser:configPassword@localhost:8888/config/eureka-server.yml`
should output:
```
eureka:
     client:
       fetchRegistry: false
       region: default
       registerWithEureka: false
       registryFetchIntervalSeconds: 5
   server:
     port: 8082
   spring:
     application:
       name: eureka-server
     redis:
       port: 6379
```


### eureka-server
```
./gradlew :eureka-server:bootRun -Dspring.profiles.active=native
```
to test it worked:
`GET` url: `http://discUser:discPassword@localhost:8082/eureka/apps`

should output something like this:
```
<?xml version="1.0" encoding="UTF-8"?>
<applications>
   <versions__delta>1</versions__delta>
   <apps__hashcode>UP_1_</apps__hashcode>
   <application>
      <name>CONFIG-SERVER</name>
      <instance>
         <instanceId>192.168.0.102:config-server:8888</instanceId>
         <hostName>192.168.0.102</hostName>
         <app>CONFIG-SERVER</app>
         <ipAddr>192.168.0.102</ipAddr>
         <status>UP</status>
         <overriddenstatus>UNKNOWN</overriddenstatus>
         <port enabled="true">8888</port>
         <securePort enabled="false">443</securePort>
         <countryId>1</countryId>
         <dataCenterInfo class="com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo">
            <name>MyOwn</name>
         </dataCenterInfo>
         <leaseInfo>
            <renewalIntervalInSecs>30</renewalIntervalInSecs>
            <durationInSecs>90</durationInSecs>
            <registrationTimestamp>1519177038956</registrationTimestamp>
            <lastRenewalTimestamp>1519177038956</lastRenewalTimestamp>
            <evictionTimestamp>0</evictionTimestamp>
            <serviceUpTimestamp>1519177038323</serviceUpTimestamp>
         </leaseInfo>
         <metadata>
            <management.port>8888</management.port>
         </metadata>
         <homePageUrl>http://192.168.0.102:8888/</homePageUrl>
         <statusPageUrl>http://192.168.0.102:8888/info</statusPageUrl>
         <healthCheckUrl>http://192.168.0.102:8888/health</healthCheckUrl>
         <vipAddress>config-server</vipAddress>
         <secureVipAddress>config-server</secureVipAddress>
         <isCoordinatingDiscoveryServer>false</isCoordinatingDiscoveryServer>
         <lastUpdatedTimestamp>1519177038956</lastUpdatedTimestamp>
         <lastDirtyTimestamp>1519176647999</lastDirtyTimestamp>
         <actionType>ADDED</actionType>
      </instance>
   </application>
</applications>
```

### zuul-server
```
./gradlew :zuul-server:bootRun -Dspring.profiles.active=native
```
if it work, services like authorization and communication will be available on port 9090
### authorization
```
./gradlew :authorization:bootRun -Dspring.profiles.active=native
```
### communication
```
./gradlew :communication:bootRun -Dspring.profiles.active=native
```
if authorization and communication works, you should be able to sendMessage and get message list

#### sendMessage
basic auth admiral//admiral
`POST` url `http://localhost:9090/api/communication/sendMessage`

requestBody JSON
```
{
	"title": "title",
	"content": "this is a content",
	"recipient": "admiral",
	"sender": "admiral"
}
```

#### getMessages
basic auth admiral//admiral
`GET` url `http://localhost:9090/api/communication/messages`
response body
```
{
	"_embedded": {
		"messageList": [
			{
				"id": 1,
				"title": "title",
				"content": "this is a content",
				"recipient": "admiral",
				"sender": "admiral"
			},
			...
```

#### forbidden exception
basic auth crewman//crewman
```
{
	"title": "title",
	"content": "this is a content",
	"recipient": "admiral",
	"sender": "crewman"
}
```
should receive 403 forbidden


## Additional information
### Service discovery (eureka)
This simplifies communication between services.
You can see an example in `com.ship.communication.controller.MessageController`.
You don't need to use a `HttpClient`. Don't forget to pass the cookie.

### Api gateway (zuul)
The api gateway makes all micro services accessible from the same endpoint
You can add AOP for all micro services

#### Security
To simplify the security, we're using basic authentication.
You have to synchronize the users you want to use in the application both in `com.ship.zuulserver.SecurityConfig` and `com.ship.authorization.service.UsersService`
 
### Config server
Centralize configuration for all micro services.

## References
 - [Spring cloud bootstrap](https://github.com/eugenp/tutorials/tree/master/spring-cloud/spring-cloud-bootstrap)
 - [Securing cloud services](http://www.baeldung.com/spring-cloud-securing-services)

