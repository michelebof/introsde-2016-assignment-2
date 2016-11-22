INTROSDE  Assignment 02: RESTful Services
===============

--------

The code
-------------
 This assignment is developed with the following technologies:

 - JAVA
 - XML/XSD
 - JSON
 - XPATH
 - JAXB/Jackson

In the /src folder there are the Java classes; the package dao ("data access object", a typical data accessing pattern) contains LifeCoachDao the enum class that manages the connection to the database.
The 'model' package contains the Person, LifeStatus, MeasureDefinition and HealthMesureHistory java classes that map to the database tables, the 'resources' package contains classes that represent the resource in REST and the default package contains the main class and it's configuration to run the standalone server.

The 'client' package contains the main class to be run to test the server, of my partner student, with the requests in both XML and JSON and to write the log files (client-server-xml.log and client-server-json.log) in the root folder.



----------

Task of the code
--------------------
With the **HealthProfile** class can execute four different tasks depending on the arguments included, the first argument that must be included is the method.
The method 'all' prints all the people in the 'people.xml' with details while the methods 'displayHealthProfile' and 'displayProfile', with the argument 'PersonID', print the HealthProfile or all the profile of the person with that id. Finally the method 'displayProfilebyWeight'  accepts a weight and an operator (=, > , <) as arguments and prints people that fulfill that condition.
With the  **JAXBmarshalling** class can marshal into NEWpeople.xml or create the NEWpeople.json with three standard people or you can unmarshal the NEWpeople.xml into the PeopleStore class and print the people's details.

------------

How run the code 
---------------------
The code can be run simply execute in the terminal ```git clone https://github.com/michelebof/introsde-2016-assignment-2```
Then:
 - ```ant start``` : to install all the dependencies and to run the standalone server
 - ```ant execute.client``` : to execute the client and save the requests/responses information into the logs file

------------

#### My heroku server:
https://introsde2016-assignment2.herokuapp.com/assignment/

#### Information of my partner student:
Name:	Sara Gasperetti

Link git:	https://github.com/SaraGasperetti

Link server heroku:	https://introsde2016-assignment-2.herokuapp.com/sdelab/ 


