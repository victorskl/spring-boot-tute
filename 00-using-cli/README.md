# 00-using-cli

- https://docs.spring.io/spring-boot/installing.html#getting-started.installing.cli

```
sdk install springboot
```

```
which spring
spring --version
spring --help
```

## shell

```
spring shell
help
help init
exit
```

## init

```
spring --help init
```

```
spring init --list
```

```
spring init --dependencies=web --build=maven my-web
```

## run

- https://docs.spring.io/spring-boot/tutorial/first-application/index.html

```
cd my-web

./mvnw --help
./mvnw dependency:tree
./mvnw spring-boot:run

ctrl-c
```

## package

```
./mvnw package
```

```
tree target
ls target/my-web-0.0.1-SNAPSHOT.jar
jar tvf target/my-web-0.0.1-SNAPSHOT.jar
```

```
java -jar target/my-web-0.0.1-SNAPSHOT.jar
```

## cleanup

```
cd ..
rm -rf my-web
```
