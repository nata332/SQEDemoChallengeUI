## Demo Challenge

#### Instructions
1. Complete the project setup as listed below
2. Complete the Excerise
3. Email a synopsis of your work and the link to your git repo containing the completed exercise to: Ruslan_Kashapau@epam.com


#### Expectations
We will be evaluating
1. Naming conventions
2. Code readability
3. Code encapsulation
4. Code structure and organization
5. Quality of test cases
6. Variety  of testing types (examples: boundary, happy path, negative, etc) 


#### Technologies
1. Java
2. Selenium
3. TestNG
4. Any other technologies you see fit.
5. Please do not use a BDD framework.

#### Project Setup
1. Clone this project to your git account in a public repo
2. Setup the project in your IDE
3. Open the index.html file from src/test/resource/files in a browser
4. Copy the url from the browser and update the url value in src/test/resource/config.properties to be the copied url.
5. In src/test/resources update the config.properties file platform for your OS.
6. From command line run mvn clean install -U -DskipTests
7. Make sure you can run the DemoTest and chrome launches.  You may need to update the chromedriver in /src/test/resources/chromedriver/ to the version that works with your browser
   https://chromedriver.chromium.org/


#### Exercise
1. Use the site at the index.html
2. There are helper locators provided for you in the src/test/resource/files/locators.txt file.
3. In the Test Cases section below:
  - List all of the test cases you think are necessary to test the sample page
  - Note any defects or issues observed
4. Code up a few examples of:
  - At least one happy path case placing an order
  - At least one error case
5. When complete please check your code into your public git repo

#### Test Cases
Please, find in SQEDemoChallengeUI\src\test\resources\files\TCs and Defects.xlsx file

#### Defects
Please, find on the second tab of the same file

#### Things For Improvement
1. Show 'Toppings 1' and 'Toppings 2' drop-downs only pizza with 1 or 2 toppings is selected
2. Rename drop-down lists from 'Pizza 1', 'Toppings 1', 'Toppings2' to smth. more representative, e.g. 'Pizza Type', 'First Topping', 'Second Topping'
3. Make 'Quantity' field a drop-down list in order to limit the number of available options
4. Re-text successful message "TOTAL: 6.75 Small 6 Slices - no toppings" to make it more clear, e.g. "TOTAL: $6.75 ORDER DETAILS: Small 6 Slices - no toppings QUANTITY: 1"
5. Disable other page content until pop-up window is not closed
6. Do not show confirmation pop-up when no pizza is selected
7. Add validation to Name and Phone fields
8. Extend maximum length of Email field to standard 320 characters (64 characters in local part, '@' and 255 in domain part)