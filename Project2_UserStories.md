# Project Name: Super Stocks
## Project Description: 
Super Stocks  is a lightweight application for tracking stocks. Each user will have a dashboard that will provide brief overview of their chosen stocks. Users will also be able to access more detailed information about individual stocks. 

## Technologies: 
- Spring Framework
- Hibernate
- Logback
- Junit
- Selenium
- Angular
- AWS EC2 Instance
- Jenkins
- SonarCloud
- twelvedata API: https://twelvedata.com/docs#getting-started


# User Stories
## User Story – 1 (**Endpoints:** Register)
**As a regular user**
- I want to be able to register for an account,
- So that I can log in and view my own stocks.

**Acceptance Criteria**
1.	Should be able to enter their first name, last name, email, password.
2.	When they click submit, they should be registered and redirected to Landing page.

## User Story – 2 (**Endpoints:** LogIn, updateUserInfo)
**As a regular user**
- I want to be able to log in,
- So that I can view my and update profile.

**Acceptance Criteria**
1.	Able to log in with username and password.
2.	Should be able to display their first name, last name, email, password(******).
3.	Should be able to update their information.

## User Story – 3 (**Endpoints:** getStocksByUserID)
**As a regular user**
- I want to be able to log in, 
- So that I can add/view/delete stocks to dashboard.

**Acceptance Criteria**
1.	Able to log in with username and password.
2.	Receive invalid login error on webpage if not providing correct credentials.
3.	Display all my stocks on the Dashboard.
4.	Add, View and Delete Stock to the Dashboard.

## User Story – 4 (**Endpoints:** updateStockByID)
**As a regular user**
- I want to be able to click on my stock displayed on Dashboard,
- So that I can view detailed stock information.
- 
**Acceptance Criteria**
1.	Able to click on stock displayed on Dashboard.
2.	Should redirect to new page.
3.	New page will have detailed stock information.


## User Story – 5 (**Endpoints:** getAllUser, updateUserInformationByID)
**As a Administrator**
- I want to be able to log in.
- So that I can view/update/delete user accounts

**Acceptance Criteria**
1.	Able to log in with username and password.
2.	Receive invalid login error on webpage if not providing correct credentials.
3.	Redirect to Admin landing page if logged in successfully.
4.	Able to update regular user information.