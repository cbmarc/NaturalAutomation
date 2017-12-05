
![CI status](https://travis-ci.org/cbmarc/NaturalAutomation.svg?branch=develop)
# NaturalAutomation #
THIS IS STILL A WORK IN PROGRESS

## Description ##

Ongoing automation framework based on JBehave, Selenium and Spring. 

This is a WorkInProgress project. I do it in my free time and there is no finish date defined.

## To use it ##
This section is also a work in progress, as we are working out the integration part with the user's testing project.
### On windows ###
```
mvn clean install -P windows
```
### On Mac ###
```
mvn clean install -P mac
```
### On Linux ###
To be added.

This will compile the project and download the web driver for your operating system. For now it will download the Chromedriver.

The chromedriver will be downloaded in your target under target/driver/. Now you can either run ```mvn test -P integration-test``` or run the 
```
com.com.naturalautomation.JBehaveStoriesIT
```
class under test scope.
## Goal ##

The goal is to have a very easy to use framework for automation. Extendable, although with a very strong core that eases the life of developers automating integration tests using selenium and BDD.
