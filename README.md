# SeleniumJBehavePoC #
THIS IS STILL A WORK IN PROGRESS
## Description ##

For now this is a prove of concept of Jbehave and Selenium using spring-boot.

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

The goal is to have a skeleton framework to use wherever needed, easily extendable and well documented. Obviously that goal is still a tad too far :)
