# assignment-rest

## Table of Contents

- [Description](#description)
- [Architecture](#architecture)
- [Endpoints and URL](#endpoints-and-url)
- [Supported Query Parameters by Search Service](#supported-query-parameters-by-search-service)
- [Response Codes](#response-codes)
- [Pre-requisite](#pre-requisite)
- [Local Build](#local-build)
- [Run JUnits](#run-junits)

## Description

Rest interface that allows searching of handset details by providing required criteria as part of query parameters.

## Architecture

- This service developed using Spring Boot which allowed easy injection of dependent objects using Spring IOC. Internally uses Spring RestTemplate to fetch details details service.

![Use Case Diagram](https://github.com/baajarmeh/assignment-rest/blob/master/user-case.png?raw=true)

## Endpoints and URL

| Method | Endpoint       |
| ------ | -------------- |
| GET    | /mobile/search?|
<br/>

## Supported Query Parameters by Search Service

| Parameter Name   |
| ---------------- |
| announceDate     |
| brand            |
| phone            |
| priceEur, price  |
| sim              |

## Response Codes

| Response Code | Description                                                  |
| ------------- | ------------------------------------------------------------ |
| 200           | Successfully processed request and returned.                 |
| 400           | Provided search criteria isn't valid for the data available. |
| 500           | Internal Error in processing request.                        |

## Pre-requisite

- Java 8, Spring Boot 2.3

This service can run locally after checking out code from github using both **gradle <TASK>** and general **java -jar** as below. As gradle doc suggests, service is build using wrapper to ensure reliable and standardized execution of build. Depending of Operating system we can run serice with gradle.bat or gradlew.

## Local Build

Building this API is as simple as running commands below

 `git clone https://github.com/baajarmeh/assignment-rest.git` <br/>
 `cd assignment-rest` <br/>
 `gradle.bat clean build`
   
## Run JUnits

When we try to build jar using `gradle.bat clean build` it runs spotless check, junits, code coverage along with prepare archive. But, if we want to 
run JUnits, then use below command(Please note as this will internally call `jacocoTestReport` to generate code coverage report).

`gradle.bat test`

Reports for tests can be found under `build/reports/tests/test` and jacoco code coverage under  `build/reports/jacoco/test/html`

