
![CI status](https://travis-ci.org/cbmarc/NaturalAutomation.svg?branch=develop)
# NaturalAutomation #
THIS IS STILL A WORK IN PROGRESS

## Description ##

Ongoing automation framework based on JBehave, Selenium and Spring. 

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

The goal is to have a very easy to use framework for automation. Extendable, although with a very strong core that eases the life of developers automating integration tests using selenium and BDD.
