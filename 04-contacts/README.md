# 04-contacts

Extracted from the Spring Security ACL sample app - contacts

- https://github.com/spring-projects/spring-security/tree/master/samples/xml/contacts


NOTES:

- mavenized
- not spring boot app
- meant to be run inside IDE or localhost tomcat to observe the sample implementation; otherwise comment from `applicationContext-common-business.xml` 

  ```
    <bean depends-on="dataSource"
        class="org.springframework.beans.factory.config.MethodInvokingBean">
      <property name="targetClass" value="org.hsqldb.util.DatabaseManagerSwing"/>
      <property name="targetMethod" value="main"/>
      <property name="arguments">
        <list>
          <value>--url</value>
          <value>jdbc:hsqldb:mem:test</value>
          <value>--user</value>
          <value>sa</value>
          <value>--password</value>
          <value></value>
        </list>
      </property>
    </bean>
  ```

- build

  ```
  mvn clean package
  ```