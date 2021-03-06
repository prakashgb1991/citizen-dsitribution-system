# Citizen Disbursement System (CDS)  using Spring Boot 

Run com.cds.CDSServiceApplication as a Java Application.

Runs on default port of Spring Boot - 8080 

## Can be run as a Jar or docker Image

cd <project folder>

mvn spring-boot:run

or

use docker command : $ docker run -p 8080:8080 prakashgb1991/citizen-distribution-system:0.0.1-SNAPSHOT


## H2 Console(currently used)

- http://localhost:8080/h2-console
- Use `jdbc:h2:mem:testdb` as JDBC URL 
- Use username: sa , password: password

## MySQL

- properties to connect to MySQL is provided in application.properties

## JUnit result

![alt text](https://github.com/prakashgb1991/citizen-dsitribution-system/blob/master/2020-07-02_11h00_25.png)

# REST API


## Get list of Users

### Request

url: [http://localhost:8080/users](http://localhost:8080/users)

**URL Params**

**Optional:**

`min=[decimal]`

`max=[decimal]`

### Response

[{"id":1,"userName":"CONE_NT_0001","salary":22.33,"updatedDate":"2020-07-01T08:01:43.540+0000","status":"A"}]

## upload users

### Request

`POST /users/upload`

**Data Params**

`file=[csv file]`

### Response

{
    "message": "Uploaded the file successfully: user.csv"
}

### Error Response

HTTP 500 Internal Server Error
