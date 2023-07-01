# WebFormAutomation-JUnit
Here the basic concepts of JUnit is used to perform automation on a web form.


## Project Summary:
In this project JUnit framework is used through selenium to perform automation testing on a web form


## Technology Used:
- JUnit
- Selenium


## Prerequisites:
- Gradle (Latest Version)


## How To Run:
Please follow these steps gradually
- Step 1: ``` git clone ```
- Step 2: ``` npm i ```
- Step 3: ``` npm test ```

To generate the report, use command ``` gradle clean test ``` on the terminal


## About Written Tests:
- Automation is done using Google Chrome webpage (Chrome WebDriver)
- However, codes are commented to use "Incognito" mode of Google Chrome & "Privaet Browsing" mode for Mozilla Firefox. Just comment out those line to use those feature. Some libarary dependency may occur, import accordingly.
- Significant gap is given between inserting values to web element to tackle anti-bot movement & also file upload delay due to network.
- Date format may vary depending on browser, please change accordingly on line 80.
- If anyone wants to change directory and name of the file that will be used to test file uploaded you may change at line 89.
- Check below for file used in this project.


## File Used
https://docs.google.com/spreadsheets/d/1rg4U80u45aUuI_aCGjEGthFNbrwi9YOT/edit?usp=sharing&ouid=115303166300822768473&rtpof=true&sd=true


## Report
![JUnitAutomation-Report](https://github.com/NzSakib017/WebFormAutomation-JUnit/assets/134344378/54d45039-2cc3-46d9-b420-659aceddcf1a)

