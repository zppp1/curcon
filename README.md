# Currency Converter

##  Tech Stack

* Java 8
* Spring Boot (Spring Web, Spring Data, Spring Security, Spring Boot Actuator)
* EclipseLink
* H2 DB
* Thymeleaf
* Guava
* Bootstrap
* jQuery

## Building

`mvn package`

## Running

`java -jar ./target/app-1.0.0.jar`

## Application URL

http://localhost:8080

## Monitoring

2 Spring Actuator endpoints enabled: 
* /health, 
* /metrics

## Settings

See application.yml

To enabling base currency selection add your paid-plan appid to application.yml and change the "fixedBase" flag to true in application.yml.