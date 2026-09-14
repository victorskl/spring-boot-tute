# 03-gs-rest-service

- The tutorial focuses on:
  - creating a simple REST API endpoint
    - https://spring.io/guides/gs/rest-service
  - springdoc-openapi
    - https://github.com/springdoc/springdoc-openapi
  - actuator
    - https://docs.spring.io/spring-boot/how-to/actuator.html
    - https://docs.spring.io/spring-boot/reference/actuator/endpoints.html#actuator.endpoints.info
  - build info
    - https://docs.spring.io/spring-boot/how-to/build.html

```
cd 03-gs-rest-service

mvn spring-boot:run
```

- http://localhost:8080/swagger-ui.html
- http://localhost:8080/api-docs
- http://localhost:8080/actuator

## curl

```
curl -s -X POST http://localhost:8080/custom | jq
curl -s http://localhost:8080/users | jq
curl -s http://localhost:8080/greeting | jq
curl -s http://localhost:8080/greeting?name=Victor | jq
```

```
curl -s http://localhost:8080/actuator | jq
curl -s http://localhost:8080/actuator/info | jq
curl -s http://localhost:8080/actuator/health | jq
```

## package

```
mvn clean package
tree target

java -jar target/03-gs-rest-service-1.0-SNAPSHOT.jar
```
