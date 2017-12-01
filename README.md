
![CI status](https://travis-ci.org/cbmarc/NaturalAutomation.svg?branch=develop)
# NaturalAutomation #
THIS IS STILL A WORK IN PROGRESS
## Description ##

For now this is a prove of concept of Jbehave and Selenium using spring-boot.

This is a WorkInProgress project. I do it in my free time and there is no finish date defined.

## To use it ##
I have not elaborated much of this, so first run a 
```
mvn clean install -DskipTests
```
The skip tests is because right now I have not separated unit test from integration tests, so selenium is run by JUnit.

Once, run the chromedriver will be downloaded in your target under target/driver/. Now you can either run a mvn test or run the 
```
com.naturalautomation.JBehaveStoriesIT
```
class under test scope.
## Goal ##

The goal is to have a skeleton framework to use wherever needed, easily extendable and well documented. Obviously that goal is still a tad too far :)
