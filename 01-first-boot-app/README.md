# 01-first-boot-app

- https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#getting-started-first-application

- https://projects.spring.io/spring-boot/

- https://spring.io/blog/2013/08/06/spring-boot-simplifying-spring-for-everyone/

```
mvn dependency:tree

mvn spring-boot:run
curl localhost:8080
ctrl+c

mvn package
tree target

java -jar target/01-first-boot-app-1.0-SNAPSHOT.jar
curl localhost:8080
ctrl+c

```