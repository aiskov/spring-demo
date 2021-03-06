Demo Spring Boot Application
============================

How to start
------------

Required software:
* JDK8
* Maven
* Docker

### Run application

Build application 

    mvn package -DskipTests dockerfile:build

Run application

    ./docker.run.sh 

Keys available:

* `--stop` - Stop containers
* `--remove` - Remove old containers
* `--recreate` - Remove containers and start again
* `./docker.run.sh <profile> ` - Run environment as directory where compose config located `./env/<profile>/docker-compose.yml`

Run unit and integration tests

    mvn verify

### Application Development

Restart server with changes 

    mvn package -DskipTests dockerfile:build && ./docker.run.sh

For debug you can use Remote Debug option in your IDE. 

    localhost:5005
 
Read logs 

    docker logs demoapp_backend_1 -f 2>&1 | less

Restart backend without rebuild (useful for changing local configuration `./env/develop/app/env.properties`)

    docker restart demoapp_backend_1
    
### Verification 

In project used one runner for Unit and Integration tests, practically it means that for test passing you should have 
environment for Integration tests. This environment should not have any collision with development, and may be used at
same time.

    ./docker.run.sh test

Then you are able to execute tests:

    mvn clean test

Available Resources
-------------------

### Endpoints

* [Clients](http://localhost:8989/clients)

### Documentation

* [Swagger UI](http://localhost:8989/swagger-ui.html#!/client-controller/listUsingGET)

### Metrics and monitoring 
* [/heapdump](http://localhost:8989/heapdump)
* [/info](http://localhost:8989/info)
* [/autoconfig](http://localhost:8989/autoconfig)
* [/loggers](http://localhost:8989/loggers)
* [/env](http://localhost:8989/env)
* [/metrics](http://localhost:8989/metrics)
* [/mappings](http://localhost:8989/mappings)
* [/beans](http://localhost:8989/beans)
* [/dump](http://localhost:8989/dump)
* [/trace](http://localhost:8989/trace)
* [/configprops](http://localhost:8989/configprops)
* [/auditevents](http://localhost:8989/auditevents)
* [/health](http://localhost:8989/health)

Technology Stack and Approaches
-------------------------------

### Basic solution

Main idea of this application is to provide example for interns and people who interesting in fast start with java. 
One of the requirements was to use "standard stack" - Spring, JPA, Maven as technological basis. 

Also was chosen approach of separation of backend and frontend. Based on single page application and REST api.

As solution is composed from several parts, it easier to implement. 

### Practices and Approaches

One of the aim of application to introduce good practices and approaches as:

* Domain Driven Design
* Command-Query Responsibility Segregation
* Behaviour Driven Development
* The twelve-factor app

# Infrastructure

* Docker & Docker-Compose - to package and run environment
* Tomcat (Embedded in SpringBoot) - to process REST API calls
* Nginx - to serve static frontend files
* MySQL - main data base

### Backend

* Spring MVC - Web framework
* Spring Security - Authentication and authorization  
* Hibernate Validation - bean validation
* Lombok - Minimize code
* Dozer - convert object
* Actuator - monitoring and health check 
* JPA/Hibernate - ORM, access to database
* ehcache - cache solution
* Swagger - auto generation of documentation
* jUnit - unit test runner
* Mockito - mock framework
* Spring Test - test context
* Maven - as package manager

### Frontend

* NPM - As package manager for JS part
* AngularJS v1.x - as tool for single page application implementation 
* Bootstrap - for layout
* Font Awesome - for graphics