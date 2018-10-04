# bp-wallet-grpc-spring


 ![bp-wallet-hld](https://user-images.githubusercontent.com/2044872/46343424-46fb5480-c65b-11e8-89e2-84bcb08c44ca.png)

### Sub Projects 

## bp-wallet-client(BPWC)
    Spring BOOT based Client Which makes Concurrenct Transaction Request via GRPC Stub over HTTP2 to BPWS.

##### Number of Transactions per Request by BPWC:

* `K Users` Should Run Concurrently.
* Each Kth User Can make `N Requests`.
* Each Nth Request can Spawn `M Concurrent Operations(Rounds)`.
* Each Round will have `DEPOSIT`, `WITHDRAW`,  `BALANCE`.
* Total Number of threads will K * M * N (* Average 7 Transactions per round).
    
## bp-wallet-server(BPWS)
    
    Keeps a record of balance in user Wallet.
    Expose API for Depositing Money, Withdrawing and Getting Balance in different currencies.
    
## bp-wallet-proto(BPWP)
    
    Has proto file(.proto), Generated Stubs and Domains shared by BPWC and BPWS.

### Assumptions and Pointers:

* Server Handles Transaction on FCFS.
* About "Make sure the client exits when all rounds has been executed."

  * BPWC exposes SWAGGER API for Testing.
  * CLI Command: Not Implemented
  
* Technologies

	* Java 8.
	* gRPC
	* MySQL and H2  
	* Gradle
	* JUnit and Test Containers
	* SLF4J
	* Docker
	* Hibernate
	* Spring and Spring Boot.
	* Swagger
	
* Junits Coverage of > 80% is OOS(Out of Scope).
* The docker containers should be run via Compose/Kubernetes. OOS.
* The Client/Server Doesn't retry failed transactions. OOS
* The user ID is Taken from Number of Users param (userID:1 for numberOfuser=1,userID:1,userID:2 for numberOfuser=2).
* Database schema has been kept Simple with One table only.
* The actual applicable schema is included in `Future Aspiration Section` with SQL and Schema Diagram.
* The Service Response/Request has been kept same for `RADIP (Rapid Application Development in Protyping)` Otherwise it should be different for each transaction type example /docs/wallet.proto.

### How to run the client and the server (run `gradlew.bat build` or `gradle build` in root project. first)

#### Database

##### LOCAL 

	If local/remote instance of MY SQL is running nothing else to do.
	Replace the IP/PORT/SCHEMA in `application.yaml` in BPWS.

##### DOCKER

Script: `start-bp-wallet-mysql-docker.bat `

	Run above in root folder it takes 5-10 Minutes to come up.
	Execute following command to get the IP of docker-machine  bash#docker-machine -ip
	Make Sure MYSQL or Any DB is UP and its properties are configured in `application.yaml`
	
#### JAVA(bp-wallet-server)

##### LOCAL 

Script: `start-bp-wallet-server.bat` 
(Please read the comments in file for more info.)

##### Docker
	
Script: `start-bp-wallet-docker.bat`
	
#### JAVA(bp-wallet-client)

##### LOCAL 

Script: `start-bp-wallet-client.bat`

##### Docker 

Script: `start-bp-wallet-docker.bat`
	
##### Access Client using 

###### LOCAL 

http://localhost:8080/swagger-ui.html#/

##### Docker

http://<dockermachine -ip>:8080/swagger-ui.html#/
	
### Request Input
`
{
  "transactions": {
    "TRANSACTION_SUCCESS": 1,
    "TRANSACTION_FAILED": 6
  },
  "timeTaken": 0
}
`
### Request Output
`
{
  "transactions": {
    "TRANSACTION_SUCCESS": 1,
    "TRANSACTION_FAILED": 600
  },
  "timeTaken": 3
}
`

`timeTaken in Seconds.`

### Important choices in Solution

* The Whole Structure of the BP-wallet application is loosely coupled SOA.
* Each Client,Server,DB Instances are developed keeping Scalability,Elasticity and Fault tolerance in mind.
* Docker Instances make it possible to enable containerization and Helps in Deployments.
* The Performance Tuning Options is not yet configurable. [Time Constraints]
* Server Side - Connection Pooling (That Depends on Given Deployment Platform)
* Client Side - Task Executer is Configurable with Concurrent Worker Threads.
* The `BPWP` is shared with Client and Server.
* Synchronization or any code level locking on DB has been avoided as there can be multiple instances running.
* Optimistic Locking is implemented (Which can also be configured for retry mechanism[Disabled for Now])
* User Registration: N number User are registered with Zero Balance at application startup (This is done to avoid user not found exception. This is a barebone approach and only adopted due to RAPID).
* Logging has been minimized via debug for improved performance.

### Transactions Per Seconds.

This is a difficult question as there are many scenarios, and requires performance tuning.

#### Per Transaction Variant:

Application Variant : All below are 10 Concurrent Calls but they take different execution time because of their nature.

* Single user Making 10 Deposit:
* Single user Making 5 Withdraws 5 Deposit:
* Single user Making 4 Withdraws 4 Deposit 2 Balance:

#### Database Variant:
	
* Embedded H2 DB:
* MYSQL DB:
* Dockerized MYSQL DB:
* Over the Cloud:
	
#### Database Connection Pooling Variant:
	
* HIKARI:
* Apache:
	
#### Platform Variant:

* Wallet Server Deployed Local Machine -(4 GB RAM 4 Cores i5):
* Wallet Server Deployed on other Machines:

### Future Aspirations.

* Cloud Ready.
* Load Balancer.
* Service Discovery.
* Authentication.
* UI Client.
* Docker Images provisioning ans Orchestration via compose.
* Caching enabled Entities - if needed.
* DB Schema.
  * Schema File - https://github.com/Akash-Mittal/bp-wallet/blob/master/docs/db-schema.sql

![bp-wallet-schema](https://user-images.githubusercontent.com/2044872/46343515-9fcaed00-c65b-11e8-88d1-a9ef37b8c6a3.png)


### Planned Features.[Implemented and Closed]

* The Proto Generation need to be done in a Separate Project.
* Although Client Should be able to generate its own stub from 
`.proto `   file , for the sake of  Loose Coupling Client and Server Need to add in proto project in ` build.gradle `

* The Server and Client are both based on Spring boot and uses grpc wrapper of spring boot that supports ` GrpcClient` and ` GrpcServer ` annotations.
* The Server will not expose Rest API's it will be called via ``` stub ``` 
* Server will be Dockerized.
