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
The project doesn't need to be installed, but for compile and execution purposes some requirements must be satisfied:
- Compile:
  - [Java JDK 8+](https://www.java.com/ "Java Site");
  - [Maven](https://maven.apache.org/ "Maven Site");
- Execution:
  - [Java JRE 8+](https://www.java.com/ "Java Site");
  
The project can be tested using [Postman](https://www.getpostman.com/ "POSTMAN").
