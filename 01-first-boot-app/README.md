# 01-first-boot-app

- https://docs.spring.io/spring-boot/tutorial/first-application/index.html#getting-started.first-application

- https://projects.spring.io/spring-boot/

- https://spring.io/blog/2013/08/06/spring-boot-simplifying-spring-for-everyone/

## dependency

```
mvn clean
mvn dependency:tree
```

## boot-run

```
mvn spring-boot:run

ctrl+c
```

```
curl localhost:8080
```

## package

```
mvn package
tree target
```

## app-run

```
java -jar target/01-first-boot-app-1.0-SNAPSHOT.jar

ctrl+c
```

```
curl localhost:8080
```
