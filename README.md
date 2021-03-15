# Automated Testing POC Instructions

## Problem solved:
This test automation solution is a POC to implement the BDD automated testing of the web applications.
The Application Under Test(AUT) for this POC is Amazon India shopping portal https://www.amazon.in/.
The test scenarios covered are - log into the application and search products from the search box.

## Technologies used:
1. Java 8.
2. [Cucumber](https://cucumber.io/ "Cucumber home") v6.8.1 for writing the BDD features.
3. [Selenium](https://www.selenium.dev/ "Selenium home") V3.141.59 for automated testing of webapps.
4. [ChromeDriver](https://chromedriver.chromium.org/ "chromedriver home") v89.0.4389.23 for executing tests on Chrome browser. 
   It will need Chrome browser version v89 or higher for the tests to execute.
5. [Apache Log4j](https://logging.apache.org/log4j/2.x/ "Log4j home") v1.2.17 for creating logs.
6. [Gradle](https://gradle.org/ "Gradle home") as build tool.

## Opening and Compiling Project:
1. [IntelliJ IDEA](https://www.jetbrains.com/idea/ "IntelliJ IDEA home") - Community edition used as IDE to develop this solution.
2. To open the project in IntelliJ, browse to 'build.gradle' file (located at '...\amazonPOC' and choose option ‘Open as Project’). 
   Make sure that project is opened as gradle project in IntelliJ.
3. In IntelliJ, go to File -> Project Structure....Under Project Settings -> Project, make sure that 'Project SDK:' selected for Java version is 1.8 or higher 
   and 'Project language level' is 8 or higher.   
4. In IntelliJ Project Explorer, verify that under 'External Libraries', JDK and libraries for Cucumber, Selenium, Log4j are loaded via Gradle
   (live internet connection required to resolve these dependencies, else, if there is a local libraries' server in the organisation,
   go to File - > Settings...Build, Execution, Deployment -> Gradle - > Use Gradle from and choose 'Specified location').
5. User should have 'write' permissions to the location where '...\amazonPOC' directory is located. This is required only for creating and writing 
   report and log files at the locations '...\amazonPOC\reports' and '...\amazonPOC\logs' respectively.
6. Ensure that 'Cucumber for Java' and 'Gherkin' plugins are installed in IntelliJ (from File -> Settings... -> Plugins).

## Running Tests:
1. From IntelliJ : 
   * To run all the tests, click the 'Run Test' icon for 'TestRunner' class (at '....\amazonPOC\src\test\java\com.amazon.test.TestRunner).
   * To run a particular feature or scenario, click the 'Run Test' icon at feature level or scenario level (feature files located at '....\amazonPOC\src\test\resources\features\).
2. From Command line (This option can also be used to run tests without requiring to build/compile the project):
   * In Command Prompt, go to Directory '.....\amazonPOC\' and run below command:
   ```text
   gradlew.bat test --tests com.amazon.test.TestRunner
   ```
   #### Note:
   Tests execute in the Chrome browser in the headless mode.
.

## Test Artifacts:
1. **Execution report**: Execution report file 'cucumber-tests.html' generated and located at '....\amazonPOC\reports'.
2. **Execution logs**: Log file 'cucumber-tests.log' generated and located at '....\amazonPOC\logs'.
