# TechAssignmentWooliesX
TechAssignmentWooliesX
This is a BDD java project based on Selenium, Cucumber and various other java based APIs including RestAssured API.

Project Structure:

src/main/java -

Package 'org.wooliesX.core' -> This package is the heart of ths framework. It handles configuration management, Drivermanagement and Factory pattern to provide the UI or API test resources to the cucumber tests. One of the main things here is that I have used same framework to be able to handle UI as well as API testing. To cater this dynamic requirement, I have used the Abstract Factory Pattern - which based on the passed runtime variables, provides the corresponding factory (out of two avvailable factories - one for UI and one for API) to tests - which in turn provides corresponding required resources to cucumber tests.

Package 'org.wooliesX.pages' -> Page Object pattern is used to handle page level test processing. 

Package 'org.wooliesX.api' -> API level test case handling is done in this package.

src/main/resources - Browser drivers are kept in this folder. Currently only chromedriver for Chrome 80 is kept there.

src/test/java -

Package 'org.wooliesX.steps' -> Step defination files for steps defined in 'placeorder.feature' are kept here.

Package 'org.wooliesX.api.steps' -> Step defination files for steps defined in 'weatherapi.feature' are kept here.

Package 'org.wooliesX.runner' -> Junit tests to start the smoke testing corresponding to UI and API

src/test/resources -

Folder 'configurations' -> This folder contains 'AppConfigurations.yaml' file which holds configurations for various environments. The configuration loading process is very dynamic using jackson apis and all it needs to add new configurations is to add in this yaml file - That's it!

Folder 'features' -> This folder holds the corresponding feature files for UI and API test scenario.

This project has been built with the following points in consideration as per the original work assigned to me:

● Ease of execution across multiple environments ● Execution Report ● Maintainability : adding new tests and changing existing ones ● Code Readability and organisation

● Ease of execution across multiple environments - I have used yaml file to hold the user specific credentials, urls for different environments in this file. During the spinning up of tests, all of this information is read from this file and based on passed runtime parameter, configuration is provided to the executing scripts for corresponding environment. No java files maintained to load the configurations into for the sake of simplicity. Configurations are loaded directly into java map and provided to test scripts to fetch data during executions.

Environment and Service variables can be passed from pom.xml as well apart from passing to mvn command line.

● Execution Report - There are few different types of reports being generated by this project - one is standard cucumber json report, other is standard cucumber html visual report and another one is the cucumber-jvm-html report which is similar to jenkins execution report generated which is pretty nice visually with graphs, tabels and different ways of analysis of execution. Once test execution is finished - following locations will have different reports - target\maven-cucumber-html\cucumber-html-reports - please open 'overview-features.html' file as the starting point. target\cucumber-html-report - please open 'index.html' to see the report. target\jsonReports\cucumber.json is the json report which is used to generate jenkins visual report.

● Maintainability : adding new tests and changing existing ones - Adding new tests is easy by adding in new feature files and pushing in new java files in corresponding packages. Maintainability of existing tests is also easy because of clear separation of concers of different packages.

● Code Readability and organisation - Code reusability is one of the main point of this project. Page objects are inheriting utility code methods from the super Page class. Apart from that BDD provides a great way to reuse lot of code by means of writing similar behaviours (steps) with varying arguments.

Pre-requisites to run: java8 and maven need to be installed to machine. 'src/main/resources' folder need to have chromedriver replaced with a different one corresponding to the chrome on the machine it is going to be executed.

Once downloaded, go to the 'TechAssignmentWooliesX' folder that means base/home folder of this project. Open the command prompt for this folder and execute this following mvn commands. Configuration file has data for three environments - st, uat and sit. So following -Denvironment variable can be modified for any of these three.

To run the UI smoke tests - "mvn compile -Denvironment=st -Dservice=chrome -Dtest=CucumberRunnerUITest test"

To run the API smoke test - "mvn compile -Denvironment=st -Dservice=api -Dtest=CucumberRunnerAPITest test"

This will start the execution. UI smoke test suite includes one test which checks that Home, Login and User Home pages are displayed as expected. That means it checks that all the ui components are available on the page. Second test checks the feature of placing the order. Incidently this test is able to figure out and error on the application - when it adds two products to cart, then each has got $2 as shipping charges and total shipping is $4. But on the cart summary page - the table shows the shipping total charge as only $2 because of which test complains there and fails. To pass this test - please open file "CartSummaryPage.java" and comment out line number 82 and 83 which is the validation check for shipping and total cost of order.