# dwp-city-users
Repository for dwp city users 
This is a REST API which returns people who are listed as either living in London, or whose current coordinates are within 50 miles of London consuming REST endpoints available at https://bpdts-test-app.herokuapp.com/

This API has one QueryParam in which we can provide Range in miles as optional parameter. In case, the range is not specified then it will use default value of 50 miles.


## Service Guide :

## API EndPoint :

**GET http://localhost:9090/London/users - returns the users who are listed as either living in London, or whose current coordinates are within 50 miles of London 

**GET http://localhost:9090/London/users?rangeInMiles=500 - returns the users who are listed as either living in London, or whose current coordinates are within 500 miles of London

***rangeInMiles is optional query param, when not provided will use default range 50 miles

*** Note - This API can be used for other cities if configure the value in application.yml file present in src/main/resources package

## Getting Started

#### Prerequisites
* Git
* JDK 8 or later
* Maven 3.0 or later

### clone

To start clone repository using below command if using git bash
git clone git@github.com:priyankachaudhary3105/dwp.git

#### To run application locally
go to class DwpCityUsersApplication in package src/main/java/org/dwp/dwpcityusers and run application as Spring Boot App

or run using maven command from project directory : mvn spring-boot:run

