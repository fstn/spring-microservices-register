[![Build Status](https://travis-ci.org/fstn/spring-microservices-register.svg?branch=master)](https://travis-ci.org/fstn/spring-microservices-register)

![Diagram](http://img15.hostingpics.net/pics/461066Springmicroservice.png)

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
    public Entity<Invoice> validate(Object entity) {
  entity = new RestHelper<Entity<Invoice>>(registerClient){
            @Override
            public Entity run(Entity<Invoice> entity) {
                /////////////////////////// DO SOMETHING ///////////////////////////
                return entity;
            }
        }.execute(entity);
        return entity;
```
* Configure application.yml and register.yml files
* Run Spring boot for register and your apps

## Try demo

* Configure register [application](https://github.com/fstn/spring-microservices-register/blob/master/microservices-registrer/src/main/resources/application.properties)
* SpringBoot run register
* Configure parent demo (world api) [application](https://github.com/fstn/spring-microservices-register/tree/master/microservices-demo-world/src/main/resources)
* SpringBoot run parent
* Configure child demo (eu api) [application](https://github.com/fstn/spring-microservices-register/tree/master/microservices-demo-child-eu/src/main/resources)
* SpringBoot run child
* Configure child demo (fr api) [application](https://github.com/fstn/spring-microservices-register/tree/master/microservices-demo-child-fr/src/main/resources)
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
  "stopAll": false,
  "stopChildren": false,
  "stackTrace": [
    {
      "app": {
        "id": "world",
        "path": "world/rest",
        "parentApp": "api",
        "hostName": "127.0.0.1",
        "port": 8081,
        "instanceId": "1"
      },
      "endPoint": {
        "path": "validate",
        "method": "POST"
      }
    },
    {
      "app": {
        "id": "eu",
        "path": "eu/rest",
        "parentApp": "world",
        "hostName": "127.0.0.1",
        "port": 8093,
        "instanceId": "1"
      },
      "endPoint": {
        "path": "validate",
        "method": "POST"
      }
    },
    {
      "app": {
        "id": "fr",
        "path": "fr/rest",
        "parentApp": "eu",
        "hostName": "127.0.0.1",
        "port": 8094,
        "instanceId": "1"
      },
      "endPoint": {
        "path": "validate",
        "method": "POST"
      }
    }
  ],
  "stackError": [],
  "data": {
    "amount": 1980.52,
    "id": "12",
    "tva": 98.2,
    "www": "www.invoice.com",
    "tvaIntraCom": "10.5",
    "header": {
      "euId": "EU145485421",
      "euCovention": "Europe Convention description",
      "supplierId": "FR121254",
      "clientId": "10001",
      "scandate": 1457035620781
    },
    "tvaFr": 10.5
  }
}
```


## Handling Error

Request:
```CURL
http://localhost:8081/world/rest/errorOnChild
```

Result:
```javascript
{
  "stopAll": false,
  "stopChildren": false,
  "stackTrace": [
    {
      "app": {
        "id": "world",
        "path": "world/rest",
        "parentApp": "api",
        "hostName": "127.0.0.1",
        "port": 8081,
        "instanceId": "1"
      },
      "endPoint": {
        "path": "errorOnChild",
        "method": "POST"
      }
    }
  ],
  "stackError": [
    {
      "app": {
        "id": "eu",
        "path": "eu/rest",
        "parentApp": "world",
        "hostName": "127.0.0.1",
        "port": 8093,
        "instanceId": "1"
      },
      "endPoint": {
        "path": "errorOnChild",
        "method": "POST"
      },
      "exception": "javax.ws.rs.InternalServerErrorException: HTTP 500 Internal Server Error HTTP 500 Internal Server Error"
    }
  ],
  "data": {
    "amount": 1980.52,
    "id": "12",
    "tva": 98.2,
    "www": "www.invoice.com"
  }
}
```


## Stop inheritance propagation 

Request:
```CURL
http://localhost:8081/world/rest/stopChildren
```

Java:
```JAVA
   @Override
            public EntityInvoice run(EntityInvoice entity) {
                this.stopAll(entity);
                return entity;
            }
        }.execute(entity);
        return entity;
```

Result:
```javascript
{
  "stopAll": false,
  "stopChildren": false,
  "stackTrace": [
    {
      "app": {
        "id": "world",
        "path": "world/rest",
        "parentApp": "api",
        "hostName": "127.0.0.1",
        "port": 8081,
        "instanceId": "1"
      },
      "endPoint": {
        "path": "stopChildren",
        "method": "POST"
      }
    }
  ],
  "stackError": [],
  "data": {
    "amount": 1980.52,
    "id": "12",
    "tva": 98.2,
    "www": "www.invoice.com"
  }
}
```

## Stop inheritance propagation All

Request:
```CURL
http://localhost:8081/world/rest/stopChildren
```

Java:
```JAVA
   @Override
            public EntityInvoice run(EntityInvoice entity) {
                this.stopChildren(entity);
                return entity;
            }
        }.execute(entity);
        return entity;
```

Result:
```javascript
{
  "stopAll": true,
  "stopChildren": false,
  "stackTrace": [],
  "stackError": [],
  "data": {
    "amount": 1980.52,
    "id": "12",
    "tva": 98.2,
    "dynamicField": {}
  }
}
```