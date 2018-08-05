# Mobile Automation Framework

This framework is created to support both iOS and Android platform automation. It is be divided into three sub-projects as below:
1. Core: Comprising of the core methods and functions. The methods enlisted in this project is application independent and can be used for automating any Android/IOS automation
2. Android: This project is application specific and comprises of loators, methods and test scripts for android application.
3. IOS: This project is application specific and comprises of loators, methods and test scripts for IOS application.


## Core
Core project consists of core methods required for automating any mobile application.

It has three basic packages as below:
### baseclass 
The base class comprises methods like mobile driver initiation, oppening Appium server and starting application under test on the device/emulator

### commonfunctions
This package has an interface implemented to facilitate function definition in both Android and IOS platforms. New generic methods should be impplemented and defined in commonfunctions class to obtain maximum code re-usability.

### utilities
The package has utility class like XLs reader and logger methods

Note: No test case or locator definition is provided in the Core project.

## AndroidProject
This is the configurable project which needs to be updated as per project requirement. basic structure of the project is expalined as below

## src/main/java
### config: 
The config file provide method to load project specific config properties as a properties file.
### pages:
This package contains all the locators and page methods as PageObject model. Each class representing a specific page in the application
### testData
This pacakge contains the data file required for providing test data in the form of excel.
### appUtilities
This package contains the application under test in .apk files.

## src/main/resorurces
contains a single config file specific to the application under test

## src/main/test
### Feature
Contains all the scenarios in form Gherkin language

### Runner
This package contains runner class for executing feature file

### StepDefinition
All the steps provided in the feature file are documented as method in step difintion file.

## Logs
Finally test execution logs are created in the logfile.log file placed in this folder.

# Installation Pre-requisites:
## Following tools must be installed to run the test case on desired machine:
	Install JDK
	Install Android SDK
	Install Android API version 4.4.2
	Install Appium for Windows
	Configure emulator using AVD manager
	Install Node JS

# Steps to execute automation tests
* Download the projects (Core and Android) in local repository
* Open Core project folder in command prompt and execute the following command. This will create the jar file for core project in maven local repository
```sh
mvn clean install
```
The above command will in return execute the following maven commands
```sh 
mvn checkstyle:checkstyle
mvn findbugs:check
mvn pmd:check
```

* Open Android emulator on local machine
* Setup config file placed in the following location. This includes emulator names and other config parameteres
```sh
..\selendroidTestApp\src\main\resources\config.properties
```
* Open Android project folder in command prompt and execute the following command
```sh
mvn clean install
```

## Report
Test execution report will be generated in the following folder
```sh
..\selendroidTestApp\target\surefire-reports
```
