![](logo.gif)

# Programming Test - QUT Data Services
##### Synopsis
This program has been written in Java 1.8 as a Spring Boot application. The provided set of RESTful endpoints have been 
implemented, with validation and are running correctly. You may test them by pointing your browser at:  
 
- [List all Questions (*Online via AWS*)](http://13.239.134.226:8080/pollservice/1.0/questions) *or*  
  
- [Specific Question (*Online via AWS*)](http://13.239.134.226:8080/pollservice/1.0/questions/e8ec31a7-e017-4615-a619-d70e7cf806e8).

The application itself is running as an EC2 off of a `t2.micro`, which is deployed into the default VPC. For the purpose of 
persisting the **questions**, a DynamoDb NoSQL database was used, as this provides the best possible performance, scalability, resilience and cost.
 
The EC2 is situated behind an ELB and with a mindfulness to production, this would no doubt involve an ASG over multiple Availability Zones.

##### How to run the Application Locally
You must have the following to run this application:
- Java 1.8 compiler 
- Apache maven 3 

Then perform the following:
```
1. mvn clean install
2. mvn spring-boot:run
```

The first command will compile the program and ensure that all the unit test cases pass. 

The second will run the console application, allowing you to connect locally to the application, should you wish, with your [browser](http://localhost:8080/pollservice/1.0/questions) i.e.: `http://localhost:8080/pollservice/1.0/questions`.
 
**Note:** It is also available over the internet, however, the choice of DynamoDB for the storage will not function correctly without a local copy of the database, if you choose to run it in this way.

##### Test Cases

During the design and coding of this application, particularly attention has been paid to TDD. A Spring​ ​Boot​ ​controller​ ​test​ ​case has been created along with a Mockito Unit Test for the internal Service layer. 
The Intellij code coverage was run, revealing that **100%** of classes and **72%** of lines were covered by the Unit/Integration Tests.

Colin Schofield   
e: colin_sch@yahoo.com  
p: 0448 644 233
