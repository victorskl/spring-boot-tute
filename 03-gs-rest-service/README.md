# 03-gs-rest-service + swagger

- https://spring.io/guides/gs/rest-service/

- http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

- http://codeboje.de/spring-boot-info-actuator/

```
cd 03-gs-rest-service

mvn spring-boot:run

mvn clean package
tree target

java -jar target/03-gs-rest-service-1.0-SNAPSHOT.jar

curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/info
curl http://localhost:8080/actuator

curl http://localhost:8080/greeting
curl http://localhost:8080/greeting?name=User

curl http://localhost:8080/v2/api-docs
```

- [http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs)

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)