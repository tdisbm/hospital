# Test Project

# Description:
Dentistry booking application

# Requirements:
- postgre database connection
- installed jdk
- free 8080 TCP port (or change it here: application.properties -> server.port)
- maven

# Run:
- open cmd
- cd to project directory
- run: mvn:package
- run: java -jar target/hospital-0.0.1-SNAPSHOT.jar

##### Note: If you are using IntellijIdea just open this project, note it as maven, then just run project.

Booking endpoint: /appointment/booking <br/>
List endpoint: /admin/appointment/list

# Tech Stack:
- Spring Boot Starter (web, data-rest, tomcat, configuration-processor)
- Lombok
- Pebble Template Engine
- Hibernate (core, validator, entity-manager), JPARest

- require.js
- bootstrap3
- bootstrap-datetimepicker
- ag-grid

# Info:
- After check please notify me to delete this repository!
