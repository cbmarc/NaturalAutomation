
![CI status](https://travis-ci.org/cbmarc/NaturalAutomation.svg?branch=develop)
# NaturalAutomation #
THIS IS STILL A WORK IN PROGRESS

## Description ##

Ongoing automation framework based on JBehave, Selenium and Spring. 

This is a WorkInProgress project. We do it in our free time and there is no finish date defined.

This project is formed of one parent project and two modules:
- natural-automation
- natural-automation-test

*natural-automation* is the framework per se.

*natural-automation-test* contains integration tests for the framework.

## To use it ##
This section is also a work in progress, as we are working out the integration part with the user's testing project.
### On windows ###
```
mvn clean install -P windows integration-test
```
### On Mac ###
```
mvn clean install -P mac integration-test
```
### On Linux ###
To be added.

This will compile the project and download the web driver for your operating system. Then it will run the stories in our project. 
The driver will be downloaded in your target under target/driver/.

You can also run it by running the 
```
com.com.naturalautomation.JBehaveStoriesIT
```
class under test scope in the natural-automation-test module.
## Goal ##

The goal is to have a very easy to use framework for automation. Extendable, although with a very strong core that eases the life of developers automating integration tests using selenium and BDD.
