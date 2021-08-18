# Offer Technical Test


## Purpose
The purpose of this technical test is to develop a Springboot API able to:
* add users from several information ;
* display the information of a desired user.


## How to use the API
This API is composed of 3 HTTP routes : 
* `GET` on `/api` : returns a list of all the users ;
* `GET` on `/api/{id}` : returns the information of the user with the id `{id}`;
* `POST` on `/api` : create a new user from these fields `username`, `dayOfBirth`, `monthOfBirth`, `yearOfBirth`,
`country` and these optional fields `gender` and `phoneNumber`.

To be registered, a user must be adult (over 18 by default), and resident of this country (France by default).


## What have been done
For this technical test, I set up an embedded database called H2, and having no persistence, that is to say the database
is reset when shutting it down. 

Regarding the code, this is a controller - service - repository architecture with 3 distinct classes :
* The **REST controller** maps and intercepts the HTTP requests, calls the associated method of the service, and returns 
a `ResponseEntity` object (with an HTTP status and a body) ;
* The **service** provides what the controller needs, and calls a repository to interact with the persistence layer ;
* The **repository** extends the `JpaRepository` interface which provides all the necessary methods to interact with the
data source.

There is also the **model** class, which defines the `User` bean, through its fields, and its constraints.

Speaking of constraints, I have used `javax.validation` and `org.hibernate.validator` packages to specify the necessary
conditions to determine if a user is valid. I have also created a custom validator for testing if the user is adult
and another one for checking if the user is a resident or not. These two annotations contain respectively an 
`requiredAge` and a `localCountry` attribute, from which the default value can be changed.

I have also written some **Javadoc** to explain how the methods work and what behaviour they should have.

Finally, I have implemented **Unit Tests** using Junit and Mockito, and reached a 100% total coverage.


## Installation and testing
Thanks to the embedded database, all you need for installing and testing this project is a JDK and a Tomcat server.

Clone this repository on your computer, open your Java IDE and let the Maven dependencies install. Then run the tests
and the server. Go to http://localhost:8080/api, and if everything is ok, you should see an empty JSON object.

Then, you can import the file `offer-technical-test.postman_collection.json` into Postman, which contains request
samples and for each one its associated tests.

**********
I hope that the quality and clarity of my code will meet your expectations, and soon have a feedback on any comments 
you might have on this project.