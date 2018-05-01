# 04-secured-rest-api

## Un-Secured App

Let start with web app development, also offer REST API service along with.

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

Now let secure the app!

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
     - mainly protect service layer methods - _service layer methods security_
     - 4 checkpoints: [Pre, Post] [Authorize, Filter]     
     - [Important concept!] [how `hasPermission()` expression link to Spring ACL system](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html#el-method-built-in)      
      - also note the handy [Method Security Meta Annotations](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html#method-security-meta-annotations) to annotate the repeating expression pattern        
      - [ JC ] https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/jc.html#jc-method      
      - [ NS ] https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/ns-config.html#ns-method-security
      
    3. applying method security
        
        - You can apply security to a single bean, using the `intercept-methods` element to ___decorate___ the bean declaration (i.e. using annotation @PreAuthorize, etc.), or you can secure multiple beans across the entire service layer using the _AspectJ_ style ___pointcuts___.
        
        - [Adding Security Pointcuts using protect-pointcut](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/ns-config.html#ns-protect-pointcut). The use of protect-pointcut is particularly powerful, as it allows you to apply security to many beans with only a simple declaration. Consider the following example:
          ```
          <global-method-security>
          <protect-pointcut expression="execution(* com.mycompany.*Service.*(..))"
            access="ROLE_USER"/>
          </global-method-security  
          ```


### domain object security (ACLs)

- Access Control List (ACL) is a list of permissions attached to an object. An ACL specifies which identities are granted which operations on a given object.

- Spring Security Access Control List is a Spring component which supports Domain Object Security. Simply put, Spring ACL helps in defining permissions for specific user/role on a single domain object – instead of across the board, at the typical per-operation level.

- https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/domain-acls.html

- http://www.baeldung.com/spring-security-acl

- add additional dependencies required for ACL
  - `org.springframework.security:spring-security-acl`
  - `org.springframework.security:spring-security-config`
  - `org.springframework.security:spring-context-support`
  - `net.sf.ehcache:ehcache-core`


### security database default schema

- https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/appendix-schema.html


