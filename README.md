LEGOLAS - Amazon Rekognition Proof of Concept
=============================================
[![Build Status](https://travis-ci.org/jomoespe/legolas-aws.svg?branch=master)](https://travis-ci.org/jomoespe/legolas-aws)

AWS implementation for Legolas service.

	
Requirements
------------

To build the project the requirements are:

  - Java 8 SDK


Build 
-----

    $ mvn clean install


Generate source and JavaDoc bundles

    $ ./mvnw source:jar
    $ ./mvnw javadoc:jar


So, to prepare all artifacts for a for a `deploy`

    $ ./mvnw clean install source:jar javadoc:jar


Runtime configuration
---------------------

To be able to invoke to *AWS Rekognition* services some environment variables must be contigured:

  - `aws.accessKeyId`
  - `aws.secretKey`
