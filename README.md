
###### Run it:

```
mvn clean install spring-boot:run
```

###### Chart API:

```
URL: http://localhost:8080/chartapi/chart
```
```
#POST request example :

POST http://localhost:8080/chartapi/chart
Authorization: Basic dXNlcjp1c2Vy
Content-Type: application/json
{
 "dimensions": [
   "team"
  ],
 "measures":[
    "champions",
    "leagues"
 ]
}
```

###### Statistics API:

```
URL: http://localhost:8080/chartapi/statistics
```
```
#POST request example:
POST http://localhost:8080/chartapi/statistics
Authorization: Basic YWRtaW46YWRtaW4=
Content-Type: application/json
{"last": 5, "timeUnit": "seconds", "mavgPoints": 3 }
```

###### Credentials and roles:

```
user:user  for  User role
admin:admin for  Admin role
```
