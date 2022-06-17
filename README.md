# Introduction

Tracking App.
> The University Lecturer has asked you to build an application in order to track the number of students and courses they choose.
A student can belong in 0 to many courses A course belongs to a dept.
> 
> Create the JSON Structure that will model this data and then will allow API calls to be made to return the List of Students in No Courses or Courses with more than 3 students.

## Description:
To read Json File, I use 3 way:
- Streaming: Best approach to parse huge (extra large) JSON file
- Abstract model Binding: use collection objects is provided by library to map with Domain Object
- Domain Model Binding: mapped directly to Domain Object

Json file path: src/main/resources/json

## Api docs:
- http://localhost:8080/swagger-ui/index.htm

> For Streaming:
>  - localhost:8080/track/v1/courses?min_students=3
>  - localhost:8080/track/v1/students/not-register

> For Abstract model Binding
>   - localhost:8080/track/v2/courses?min_students=3
>   - localhost:8080/track/v2/students/not-register

> For Domain Model Binding
>   - localhost:8080/track/v3/courses?min_students=3
>   - localhost:8080/track/v3/students/not-register

## Run App:
- java require: jdk 11
### We can run app follow 2 way: 
#### 1: Run directly jar file in folder
- access folder: < project_path >/lib
- run command: java -jar tracking-app.jar

#### 1: Run through docker
- access folder < project_path >
- run command: docker build -t demo/tracking-app .
- run command: docker run -d -p 8080:8080 -t demo/tracking-app