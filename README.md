[![Build Status](https://travis-ci.org/fstn/spring-microservices-register.svg?branch=master)](https://travis-ci.org/fstn/spring-microservices-register)
# spring-microservices-register
Microservice register like netflix Eureka
## Explanation

**Spring Microservice Register** provide a way to flow rest service API from parent to child
###Exemple
```javascript
api -> world -> eu -> fr
```
When calling discover on  /api, it will call discover on /world, on /eu and on /fr.
####List available Apps
You can list register apps by calling 
```CURL
GET http://{register host}:{register port}/apps/
```
Return result:
```javascript
[{"app":"eur","path":"eur","parentApp":"world","hostName":"127.0.0.1","status":"STARTING","port":8093,"instanceID":"1","endPoints":[{"path":"validateInvoice","method":"POST"}]},{"app":"world","path":"world","parentApp":"api","hostName":"127.0.0.1","status":"STARTING","port":8080,"instanceID":"1","endPoints":[{"path":"validateInvoice","method":"POST"}]}]
```
####List App children
You can list children by calling 
```CURL
GET http://{register host}:{register port}/apps/{appID}/{instanceID}/children
```
Return result:
```javascript
[{"app":"eur","path":"eu/rest","parentApp":"world","hostName":"127.0.0.1","status":"STARTING","port":8093,"instanceID":"1","endPoints":[{"path":"validateInvoice","method":"POST"},{"path":"invoices","method":"GET"}]}]
```

##Quick start
* Inject registerClient inside your RestController exemple: [RestController.java](https://github.com/fstn/spring-microservices-register/blob/master/microservices-demo-child/src/main/java/com/microServices/rest/RestController.java)

```java
    @Inject
    RegisterClient registerClient;
```
* Use RegisterClient Method to execute on Child

```java
     @POST
    @Path("validate")
    @Consumes("application/json")
    @Produces("application/json")
    public Entity<Invoice> validate(Entity<Invoice> entity) {
/**
 CALL BEFORE CHILDREN
**/
        entity  = (Entity)registerClient.executeOnChildren(new EndPoint("validate", HttpMethod.POST, EndPoint.ExecutePosition.BEFORE),entity,new GenericType<Entity<Invoice>>(){});
/**
 DO SOMETHING
blabla
**/
        entity = registerClient.addStackCall(entity,new EndPoint("validate",HttpMethod.POST,EndPoint.ExecutePosition.PARENT));

/**
 CALL AFTER CHILDREN
**/
        entity  = (Entity)registerClient.executeOnChildren(new EndPoint("validate", HttpMethod.POST,EndPoint.ExecutePosition.AFTER),entity,new GenericType<Entity<Invoice>>(){});
        return entity;
    }
```
* Configure application.yml and register.yml files
* Run Spring boot for register and your apps

## Try demo

* Configure register [application.yml](https://github.com/fstn/spring-microservices-register/blob/master/microservices-registrer/src/main/resources/application.yml)
* SpringBoot run register
* Configure parent demo (world api) [.yml](https://github.com/fstn/spring-microservices-register/tree/master/microservices-demo-parent/src/main/resources)
* SpringBoot run parent
* Configure child demo (eu api) [.yml](https://github.com/fstn/spring-microservices-register/tree/master/microservices-demo-child/src/main/resources)
* SpringBoot run child
* Try calling parent:

```CURL
POST http://{parent host}:{parent port}/{parent context path}/validate
{
    "data":{  
   "id":12,
   "tva":98.20,
   "amount":1980.52
},
"stackTrace":[
    ]
}
```
* In result JSON you will see child call in stackTrace, exemple

```javascript
{
  "stackTrace": [
    {
      "app": {
        "app": "eu",
        "path": "eu/rest",
        "parentApp": "world",
        "hostName": "127.0.0.1",
        "status": "STARTING",
        "port": 8093,
        "instanceID": "1",
        "endPoints": [
          {
            "path": "validate",
            "method": "POST",
            "executePosition": "BEFORE"
          },
          {
            "path": "invoices",
            "method": "GET",
            "executePosition": "AFTER"
          }
        ]
      },
      "endPoint": {
        "path": "validate",
        "method": "POST",
        "executePosition": "PARENT"
      }
    },
    {
      "app": {
        "app": "world",
        "path": "world/rest",
        "parentApp": "api",
        "hostName": "127.0.0.1",
        "status": "STARTING",
        "port": 8080,
        "instanceID": "1",
        "endPoints": [
          {
            "path": "validate",
            "method": "POST",
            "executePosition": "BEFORE"
          },
          {
            "path": "invoices",
            "method": "GET",
            "executePosition": "AFTER"
          }
        ]
      },
      "endPoint": {
        "path": "validate",
        "method": "POST",
        "executePosition": "PARENT"
      }
    }
  ],
  "data": {
    "amount": 1980.52,
    "id": "12",
    "tva": 98.2,
    "dynamicField": {
      "TVA_INTRACOM": 10.5,
      "WWW": "www.test.com"
    }
  }
}
```


