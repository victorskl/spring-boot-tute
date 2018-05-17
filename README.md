# spring-boot-tute

Spring Boot Exercises 

_note: tutes are overly commented for learner purpose_

### bootstrap 

- To bootstrap Spring Boot Maven project skeleton structure

```
$ git clone https://github.com/victorskl/spring-boot-tute.git
$ cd spring-boot-tute

$ bash bootstrap.sh my-boot-app

$ tree my-boot-app
my-boot-app
├── README.md
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── Application.java
    │   └── resources
    │       └── application.properties
    └── test
        ├── java
        │   └── ApplicationTest.java
        └── resources
            └── application.properties

7 directories, 6 files
```

- Alternatively, use the [https://start.spring.io](https://start.spring.io) or through [`spring` CLI](00-using-cli)

```
$ spring init -g com.sankholin my-boot-app

$ tree my-boot-app
my-boot-app
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── sankholin
    │   │           └── mybootapp
    │   │               └── DemoApplication.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
            └── com
                └── sankholin
                    └── mybootapp
                        └── DemoApplicationTests.java

12 directories, 6 files
```