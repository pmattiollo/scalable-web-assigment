# scalable-web-assigment
Project for a Scalable Web Assigment to WAES Company

- Providing 2 http endpoints that accepts JSON base64 encoded binary data on both endpoints:
  - <host>/v1/diff/<ID>/left;
  - <host>/v1/diff/<ID>/right;
- Providing 1 http endpoint for diff the provided data and show the results:
  - <host>/v1/diff/<ID>;
  - The results provided follow the the JSON format:
    - If equal return that;
    - If not of equal size just return that;
  	- If of same size provide insight in where the diffs are (so mainly offsets + length in the data).
   
## Getting Started
The project doesn't need to be installed, but for build and execution purposes some requirements must be satisfied:
- Build and tests:
  - [Java JDK 8](https://www.java.com/ "Java Site");
  - [Maven 3+](https://maven.apache.org/ "Maven Site");
- Execution:
  - [Java JRE 8](https://www.java.com/ "Java Site");
  
The project can be tested by using [Postman](https://www.getpostman.com/ "POSTMAN Site"), [Swagger](https://swagger.io/ "Swagger Site") or [Docker](https://www.docker.com/ "DOCKER Site").

### Check the requirements
After the installation of Java and Maven, you can check if they have been successful installed:
```sh
java -version
```
And:
```sh
mvn -version
```

## Installation
To perform the project installation you must clone or download the [repository](https://github.com/pmattiollo/scalable-web-assigment "Github repository") into a directory and use Maven as following:
```sh
cd scalable-web-assigment
mvn package
```
It will generate a Fat jar that can gets executed in any machine since Java is installed.

## Running the tests
The unit and integration tests require Maven. It can performed in a console (cmb, bash, etc.) or over an IDEA (IntelliJ, Eclipse, etc.). The output will be displayed at the same console:
```sh
cd scalable-web-assigment
mvn test
```
It will run all the unit and integration tests.

## Running the application
The execution requires Java and, by default, the port 8080 is going to used, so before you run the application make sure that port is ready to use. Furthermore, make sure the project has been sucessfully installed as described above. It can performed in a console (cmb, bash, etc.) or over an IDEA (IntelliJ, Eclipse, etc.). The output will be displayed at the same console:
```sh
cd scalable-web-assigment
java -jar target/scalable-web-assigment-0.0.1-SNAPSHOT.jar
```
You can check the application status using any browser acessing `http://localhost:8080/actuator/health`.

## Rest API
All details of Rest API, including design, documentation and consuming can be visualized using Swagger Framework. You can see how API works or even test the application using any browser, accessing the provided endpoint `http://localhost:8080/swagger-ui.html`.


## Built With
- IntelliJ - IDE (Integrated Development Environment);
- Java (Version 8) - Computer programming language;
- Spring Framework - Complete framework with different modules (MVC, JPA, Security, etc.) ;
- Swagger Framework - OAS (OpenAPI Specification);
- Maven - Dependency Management;
- JUnit - Unit testing framework;
- Mockito - Mocking framework for testing purpouse;
- Tomcat - Embedded version of a servlet container provided by the Spring framework;
- H2 - Open-source lightweight Java database.

## Contributing
Any contribuition is welcome. Every pull request will be carefully evaluated.

## Authors
- **[Pedro Humberto Mattiollo](www.linkedin.com/in/pmattiollo)**
