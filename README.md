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

* Server Handles Transaction on FCFS.Ideally in a solution like this the sequence of transactions must be maintained viz OOS.
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
	* Springs Transaction.
	
* Junits Coverage of > 80% is OOS(Out of Scope).
* The docker containers should be run via Compose/Kubernetes. OOS.
* The Client/Server Doesn't retry failed transactions. OOS
* The user ID is Taken from Number of Users param (userID:1 for numberOfuser=1,userID:1,userID:2 for numberOfuser=2).
* Database schema has been kept Simple with One table only.
* The actual applicable schema is included in `Future Aspiration Section` with SQL and Schema Diagram.
* The Service Response/Request has been kept same for `RADIP (Rapid Application Development in Protyping)` Otherwise it should be different for each transaction type example /docs/wallet.proto.
* There is limited caching implemented `spring-kv-caching` however its performance has not been benchmarked yet.

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
	
### Request

	{
	  "numberOfRequests": 1,
	  "numberOfRounds": 1,
	  "numberOfUsers": 100
	}

### Reponse

	{
	  "transactions": {
	    "TRANSACTION_SUCCESS": 700,
	    "TRANSACTION_FAILED": 0
	  },
	  "timeTaken": 2
	}


`timeTaken in Seconds.`

Note:For very quick start up please import the project in STS and run `BPWS` and `BPWC` as `spring boot app`.

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
* Some of the Decisions and choices are evident from TPS section.

### Transactions Per Seconds[TPS].

This is a difficult question as there are various permutation and combination with each variant, and requires performance tuning to reach a common objective or to hadnle any `future spikes`.

#### Per Transaction Variant:

Application Variant : All below are 10 Concurrent Calls but they take different execution time because of their nature.

* Single user Making 10 Deposit: Corresponds to 20 DB Calls 10 for Get and 10 for Update.
* Single user Making 5 Withdraws 5 Deposit:Same.
* Single user Making 4 Withdraws 4 Deposit 2 Balance: 18 DB Calls 10 Get and 8 Update.

All of the above transactions have high chances of` OptimisticLockException` due to versioning on stale object.This can overcome by retry mechanism however in this kind of scenario the sequence need to be guranteed which is achievable by bidirectional streaming.

Then there are other scenarios with multiple users with multiple transactions - testing is OOS.

#### Database Variant:
	
* Embedded H2 DB:only used for RAPID.
* MYSQL DB: Although mysql can handle 150+1 Connections , for that the calling system should be of very high configurations, The currenct application configures the Connection Pool Max Size to be 10 based `Number of Cores * 2 + Max(tX Spindle time)`
* Dockerized MYSQL DB: This makes a delta of 5-10 %.
	
#### Database Connection Pooling Variant:
	
* HIKARI:If the number of transaction grows > 400 HCP starts getting `Connection Not Available`, to avoid this the session state was properly synched with DB using flush in finally block.The connection time out was tweaked to 3 Minutes from default.This alone is not suffieient there need to be Data replication in MYSQL with Application Caching for outstanding performance which OOS.
* Apache:OOS
	
#### Platform Variant: 

* Wallet Server Deployed Local Machine -(4 GB RAM 4 Cores i5):At large 100+-.
* Wallet Server Deployed on other Machines:OOS


Having said that, I have been able to achieve about 120(+-) concurrenct reuqests persecond , Alghough it may very on machines for example i was able to make about 1000+- Transactions in about 1 second for one user deposit only.

Apart from this - there is another POC written which executes for one second so to better understand the stages of optimization.
The over all goal was to run it wit time command and check the actual `CPU utilization`.


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
