#!/usr/bin/env bash

[ $# -eq 0 ] && { echo "Usage: $0 my-boot-app-name"; exit 1; }

APP=$1

mkdir -p ${APP}/src/main/java/start
mkdir -p ${APP}/src/main/resources

cat >${APP}/src/main/java/Application.java <<EOF
package start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
EOF

cat >${APP}/src/main/resources/application.properties <<EOF
info.app.encoding=@project.build.sourceEncoding@
info.app.java.source=@java.version@
info.app.java.target=@java.version@
info.app.version=@project.version@
info.app.name = @project.name@
info.app.groupId = @project.groupId@
info.app.artifactId = @project.artifactId@
EOF

mkdir -p ${APP}/src/test/java/start
mkdir -p ${APP}/src/test/resources

cat >${APP}/src/test/java/ApplicationTest.java <<EOF
package start;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
	@Test
	public void contextLoads() {
	}
}
EOF

cat >${APP}/src/test/resources/application.properties <<EOF
# placeholder
EOF

cat >${APP}/README.md <<EOF
# ${APP}
My Spring Boot App
EOF

cat >${APP}/.gitignore <<EOF
.DS_Store
.idea
*.iml
target
*.class
*.jar
*.war
EOF

cat >${APP}/pom.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>start</groupId>
    <artifactId>${APP}</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>My Spring Boot App</name>

    <properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
    </properties>

    <!-- https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#using-boot-maven -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.1.RELEASE</version>
    </parent>

    <dependencies>
        <!-- spring boot starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- others -->

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
EOF