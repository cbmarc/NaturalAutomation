![CI status](https://travis-ci.org/cbmarc/NaturalAutomation.svg?branch=develop)
# NaturalAutomation #
THIS IS STILL A WORK IN PROGRESS
## Description ##

Ongoing automation framework based on JBehave, Selenium and Spring.

This is a WorkInProgress project. I do it in my free time and there is no finish date defined.

## To use it ##
First run:
```
mvn clean install
```

Once run the chromedriver will be downloaded in your target under target/driver/. Now you can either run a 

To execute the integration test
```
mvn clean install -P windows,integration-test
```

## Goal ##

The goal is to have a very easy to use framework for automation. Extendable, although with a very strong core that eases the life of developers automating integration tests using selenium and BDD.
