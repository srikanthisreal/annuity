# Coding challenge
## Plan Generator
In order to inform borrowers about the final repayment schedule, we need to have pre-calculated repayment plans throughout the lifetime of a loan.
To be able to calculate a repayment plan specific input parameters are necessary:
duration (number of installments in months)
nominal rate (annual interest rate)
loan amount (principal amount)
Date of Disbursement/Payout ("startDate")
These four parameters need to be input parameters.
The goal is to calculate a repayment plan for an annuity loan. Therefore the amount that the borrower has to pay back every month, consisting of principal
and interest repayments, does not
change (the last installment might be an exception).
The annuity amount has to be derived from three of the input parameters (duration, nominal interest rate, total loan amount) before starting the plan
calculation.
(use http://financeformulas.net/Annuity_Payment_Formula.html as reference)
 Note: the nominal interest rate is an annual rate and must be converted to monthly before using in the annuity formula 
 
## Install and Run
### Requirements
1. Docker/Docker Compose
2. maven
3. JDK 1.8
4. Git client


### How to Run with Docker and with Maven?
Current setup provides you two ways to run the application

**Development mode**
- Open the project in your favourite IDE (Ex. IntelliJ IDEA).
```
- mvn clean install

- mvn spring-boot:run

Application resources available:
 - Swagger API Docs:  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
 
Using swagger try it now, API can be triggered. 
 - Post /api/v1/annuity/generate-plan
 - Request > change the default start date and trigger
 - Request Object : 
{
  "duration": 0,
  "loanAmount": 0,
  "nominalRate": 0,
  "startDate": "2020-12-01T00:44:026Z"
}

- curl -X POST "http://localhost:8080/api/v1/annuity/generate-plan" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{  \"duration\": 0,  \"loanAmount\": 0,  \"nominalRate\": 0,  \"startDate\": \"2020-12-01T00:44:026Z\"}"

**Production mode**

* mvn clean install
* docker build -t annuity .
* docker run -p 8080:8080 -it annuity

Application resources available:
 - Swagger API Docs:  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

-CURL Command

* curl -u user:password  --header "Content-Type: application/json" -X POST  --data '{"loanAmmount" : 5000,"nominalRate" : 5,"duration" : 24,"startDate": "01.01.2018"}' http://localhost:8080/generatePlan

-OUTPUT FILE

* curl -u user:password  --header "Content-Type: application/json" -X POST  --data '{"loanAmmount" : 5000,"nominalRate" : 5,"duration" : 24,"startDate": "01.01.2018"}' http://localhost:8080/generatePlan --output output.json

-Windows Docker Tool Box

* curl -u user:password  --header "Content-Type: application/json" -X POST  --data '{"loanAmmount" : 5000,"nominalRate" : 5,"duration" : 24,"startDate": "01.01.2018"}' http://192.168.99.100:8080/generatePlan --output output.json

-verify output.json in current directory



## Tech stack & Open-source libraries

Server - Backend

* 	[JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java Platform, Standard Edition Development Kit
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[Junit 5](https://junit.org/junit5/docs/current/user-guide/) - Distributed version control system
* 	[Maven](https://maven.apache.org/) - Dependency Management
* 	[Git](https://github.com/) - Version Controller 
* 	[Docker](https://www.docker.com/) - Docker is a set of platform as a service products that use OS-level virtualization to deliver software in packages called containers. Containers are isolated from one another and bundle their own software, libraries and configuration files; they can communicate with each other through well-defined channels

External Tools & Services

* 	[Postman](https://www.getpostman.com/) - API Development Environment (Testing Docmentation)