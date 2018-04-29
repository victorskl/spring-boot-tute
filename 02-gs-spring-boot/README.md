# 02-gs-spring-boot

- Tute focus: 
  - spring-boot-starter
  - spring-boot-test
  - Unit Test, Integration Test
  - Maven surefire, failsafe plugin

- https://spring.io/guides/gs/spring-boot/

```
cd 02-gs-spring-boot
    
mvn package
tree target

java -jar target/02-gs-spring-boot-1.0-SNAPSHOT.jar
curl localhost:8080

ctrl+c

(run unit test)
mvn test

(run integration test)
mvn verify

mvn package && java -jar target/02-gs-spring-boot-1.0-SNAPSHOT.jar

curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/info
curl http://localhost:8080/actuator
```

Use more starter-*

- https://docs.spring.io/spring-boot/docs/2.0.1.RELEASE/reference/htmlsingle/#using-boot-starter