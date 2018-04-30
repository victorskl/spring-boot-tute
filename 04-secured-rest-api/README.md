# 04-secured-rest-api

### start unsecure web app and REST api

- add starter-web
- add `io.springfox:springfox-swagger2` and `io.springfox:springfox-swagger-ui` for REST API at /swagger-ui.html


### thymeleaf

- add starter-thymeleaf
- add `org.thymeleaf.extras:thymeleaf-extras-springsecurity4`
- https://spring.io/guides/gs/serving-web-content/
- http://www.baeldung.com/spring-boot-start
- http://www.baeldung.com/thymeleaf-in-spring-mvc
- http://www.baeldung.com/spring-security-thymeleaf

### testing

- add starter-test
- add starter-actuator


### logging

- https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-logging.html
- https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html
- https://www.mkyong.com/spring-boot/spring-boot-slf4j-logging-example/


### lombok

- add `org.projectlombok:lombok`
- https://projectlombok.org/setup/intellij


### sql database

- add starter-jpa
- add `com.h2database:h2`
- `spring.h2.console.enabled=true` at /h2-console
- activate dev profile `-Dspring.profiles.active=dev`
- https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-sql.html
- https://docs.spring.io/spring-boot/docs/current/reference/html/howto-data-access.html
- https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html
- add `org.flywaydb:flyway-core`
- https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html#howto-use-a-higher-level-database-migration-tool


## Security

### secure the app

- add starter-security
- https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-security.html
- https://docs.spring.io/spring-boot/docs/current/reference/html/howto-security.html
- https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/guides/html5/helloworld-boot.html


### expression-based access control

- https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html

- Mainly boil down to two key expressions:
  1. Role-Based Access Control - RBAC - _hasRole("ADMIN")_
  
  2. Permission-Base Access Control - PBAC - _hasPermission(Object target, Object permission)_

- Securing layers:

  1. [Web Security](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html#el-access-web) 
      - mainly protect URL/path
      - for REST, it is also possible to protect based-on the resource path and path variable e.g. `/api/studies/{study_id}` and put forward authority check on this resource request
    
  2. [Method Security](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html#method-security-expressions)
     - mainly protect service layer methods
     - 4 checkpoints: [Pre, Post] [Authorize, Filter]
     - [Important concept!] [how `hasPermission()` bridge into Spring ACL system](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html#el-method-built-in)
      - also note the handy [Method Security Meta Annotations](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html#method-security-meta-annotations) to annotate the repeating expression pattern
      - _more on following section..._


### method security

_service layer methods security_

- [ JC ] https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/jc.html#jc-method

- [ NS ] https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/ns-config.html#ns-method-security


### domain object security (ACLs)

- https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/domain-acls.html


### security database schema

- https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/appendix-schema.html

- http://www.baeldung.com/spring-security-acl


