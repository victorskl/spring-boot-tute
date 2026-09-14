# 02-gs-spring-boot

- The tutorial focuses on: 
  - spring-boot-starter
  - spring-boot-test
  - Unit Test, Integration Test
  - Maven surefire, failsafe plugin

- https://docs.spring.io/spring-boot/reference/testing/test-modules.html
- https://docs.spring.io/spring-boot/reference/testing/test-scope-dependencies.html
- https://docs.spring.io/spring-boot/reference/testing/spring-boot-applications.html

```
cd 02-gs-spring-boot

mvn clean
mvn compile
mvn dependency:tree
```

```
(run unit test)

mvn test
```

```
(run integration test)

mvn verify
```

```
mvn package && java -jar target/02-gs-spring-boot-1.0-SNAPSHOT.jar

curl -s http://localhost:8080/actuator | jq
curl -s http://localhost:8080/actuator/info | jq
curl -s http://localhost:8080/actuator/health | jq
curl -s http://localhost:8080/actuator/health/liveness | jq
curl -s http://localhost:8080/actuator/health/readiness | jq
```

---

# Differences Between the 3 Tests

| Aspect                              | `HelloControllerTest`   | `HelloControllerMvcTest`              | `HelloControllerIT`             |
|-------------------------------------|-------------------------|---------------------------------------|---------------------------------|
| Test type                           | Pure unit test          | MVC integration test                  | Full integration test           |
| Spring application context          | No                      | Yes                                   | Yes                             |
| Embedded web server                 | No                      | No                                    | Yes                             |
| Network port                        | No                      | No                                    | Yes, random port                |
| Test client                         | Direct Java method call | `MockMvc`                             | `TestRestTemplate`              |
| HTTP request                        | No                      | Simulated                             | Real                            |
| Controller method called            | Directly                | Through Spring MVC                    | Through the complete HTTP stack |
| Tests request mappings              | No                      | Yes                                   | Yes                             |
| Tests HTTP status and response      | No                      | Yes                                   | Yes                             |
| Tests Spring MVC configuration      | No                      | Yes                                   | Yes                             |
| Tests embedded server configuration | No                      | No                                    | Yes                             |
| Tests real HTTP communication       | No                      | No                                    | Yes                             |
| Typical execution speed             | Fastest                 | Fast                                  | Slowest                         |
| Typical scope                       | One class or method     | Web/MVC layer                         | Whole application               |
| Typical class name                  | `HelloControllerTest`   | `HelloControllerMvcTest`              | `HelloControllerIT`             |
| Main purpose                        | Test logic in isolation | Test MVC behaviour without networking | Test the running application    |

## Request Flow

| Test                     | Request flow                                                          |
|--------------------------|-----------------------------------------------------------------------|
| `HelloControllerTest`    | Test → controller method                                              |
| `HelloControllerMvcTest` | `MockMvc` → Spring MVC → controller                                   |
| `HelloControllerIT`      | `TestRestTemplate` → HTTP → embedded server → Spring MVC → controller |

## Summary

- **`HelloControllerTest`**: fastest and most isolated.
- **`HelloControllerMvcTest`**: tests Spring MVC without starting a server.
- **`HelloControllerIT`**: tests the application through a real HTTP connection.

---

## Test execution

| Command      | Tests run                                                     |
|--------------|---------------------------------------------------------------|
| `mvn test`   | Surefire tests, such as `*Test`                               |
| `mvn verify` | Surefire tests plus Failsafe integration tests, such as `*IT` |

## Failsafe

Failsafe automatically detects the conventional integration-test names:

```text
IT*.java
*IT.java
*ITCase.java
```

## Surefire

Surefire does not normally include `*IT` classes by default anyway.
