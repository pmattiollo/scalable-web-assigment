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
    - If of same size provide insight in where the diffs are (so mainly offsets + length in the data);
   
## Getting Started
The project doesn't need to be installed, but for build and execution purposes some requirements must be satisfied:
- Build:
  - [Java JDK 8+](https://www.java.com/ "Java Site");
  - [Maven](https://maven.apache.org/ "Maven Site");
- Execution:
  - [Java JRE 8+](https://www.java.com/ "Java Site");
  
The project can be tested by using [Postman](https://www.getpostman.com/ "POSTMAN Site") or [Docker](https://www.docker.com/ "DOCKER Site"). Before you run the project make sure that PORT 8080 is free to use.

## Running the tests
The unit and integration tests can be run using Maven in a console (cmb, bash, etc.) or over an IDEA (IntelliJ, Eclipse, etc.). The output execution is going to be displayed in the same console.

```bash
cd scalable-web-assigment
mvn test
```
This will run the entire project unit and integration tests.

## Built With
- IntelliJ - IDE (Integrated Development Environment);
- Java (Version 8) - Computer programming language;
- Spring Framework - Complete framework with different modules (MVC, JPA, Security, etc.) ;
- Maven - Dependency Management;
- JUnit - Unit testing framework;
- Mockito - Mocking framework for testing purpouse;
- Tomcat - Embedded version of a servlet container provided by the Spring framework;
- H2 - Open-source lightweight Java database.

## Contributing
Any contribuition is welcome. Every pull request will be carefully evaluated.

## Authors
- **[Pedro Humberto Mattiollo](www.linkedin.com/in/pmattiollo)**
