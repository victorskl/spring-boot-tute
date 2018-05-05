# 04-secured-rest-api

Focus on protecting Spring Boot REST API service application using Spring Securiy and Spring Security ACL

## Un-Secured App

Let start with typical web mvc application development which also offer REST API service along with it.

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

___EBAC___

- https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html

- Mainly boil down to two key expressions:
  1. Role-Based Access Control - ___RBAC___ - _hasRole("ADMIN")_
  
  2. Permission-Base Access Control - ___PBAC___ - _hasPermission(Object target, Object permission)_

- Securing layers:

  1. [Web Security](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html#el-access-web) 
      - mainly protect URL, path or resource
      - intercept the URL path pattern and enforce the access on it by defining expression access control _i.e. hasRole() or hasPermission() or hasMyCustomExpression()_
      - the [access expression can be also backed by Plain-Old Spring Bean (_oh yah, POS-Bean!_ ;o)](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html#el-access-web-beans) which implement some sort of custom access rule for your business logic
      - for REST perspective, it is also possible to [protect based-on the resource path and path variable](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html#el-access-web-path-variables) e.g. `/api/studies/{study_id}` and put forward authority check on this resource request at backing POS-Bean
    
  2. [Method Security](https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/el-access.html#method-security-expressions)
     - mainly protect service layer methods - _service layer methods security_
     - popular choice for REST API service protection
     - 4 checkpoints: [Pre, Post] + [Authorize, Filter]     
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

___DOS-ACL___

- add additional dependencies required for ACL
  - `org.springframework.security:spring-security-acl`
  - `org.springframework.security:spring-security-config`
  - `org.springframework.security:spring-context-support`
  - `net.sf.ehcache:ehcache-core`

#### concept

- https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/domain-acls.html

- http://www.baeldung.com/spring-security-acl

- Access Control List (ACL) is a list of permissions attached to an object. An ACL specifies which identities are granted which operations on a given object.

- Spring Security Access Control List is a Spring component which supports Domain Object Security. Simply put, Spring ACL helps in defining permissions for specific user/role on a single domain object – instead of across the board, at the typical per-operation level.

- DOS-ACL works in tandem with EBAC's RBAC and/or PBAC
  - especially, by using DOS-ACL system integer bit masking with PBAC, the permissions can be applied at finer granular level on the concerning domain object: e.g. read (bit 0), write (bit 1), create (bit 2), delete (bit 3) and administer (bit 4) on your domain object (e.g. Contact object in contact-management-system) - _aka permission attributes_


#### security database default schema

- https://docs.spring.io/spring-security/site/docs/4.2.5.RELEASE/reference/html/appendix-schema.html

2.1. ACL Database

To use Spring Security ACL, we need to create ___four mandatory tables___ in our database.

- The first table is `ACL_CLASS`, which store class name of the domain object, columns include:

  - `ID` 
  - `CLASS`: the class name of secured domain objects

- Secondly, we need the `ACL_SID` table which allows us to universally identify any principle or authority in the system. The table needs:

  - `ID`
  - `SID`: which is the username or role name. `SID` stands for Security Identity
  - `PRINCIPAL`: 0 or 1, to indicate that the corresponding `SID` is a principal (user, such as mary, mike, jack…) or an authority (role, such as `ROLE_ADMIN`, `ROLE_USER`, `ROLE_EDITOR`, etc)

- Next table is `ACL_OBJECT_IDENTITY`, which stores information for each unique domain object:

  - `ID`
  - `OBJECT_ID_CLASS`: define the domain object class, links to ACL_CLASS table
  - `OBJECT_ID_IDENTITY`: domain objects can be stored in many tables depending on the class. Hence, this field store the target object primary key
  - `PARENT_OBJECT`: specify parent of this Object Identity within this table
  - `OWNER_SID`: ID of the object owner, links to ACL_SID table
  - `ENTRIES_INHERITTING`: whether ACL Entries of this object inherits from the parent object (ACL Entries are defined in ACL_ENTRY table)

- Finally, the `ACL_ENTRY` store individual permission assigns to each `SID` on an Object Identity:

  - `ID`
  - `ACL_OBJECT_IDENTITY`: specify the object identity, links to `ACL_OBJECT_IDENTITY` table
  - `ACL_ORDER`: the order of current entry in the ACL entries list of corresponding Object Identity
  - `SID`: the target `SID` which the permission is granted to or denied from, links to `ACL_SID` table
  - `MASK`: the integer bit mask that represents the actual permission being granted or denied
  - `GRANTING`: value 1 means granting, value 0 means denying
  - `AUDIT_SUCCESS` and `AUDIT_FAILURE`: for auditing purpose
