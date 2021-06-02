# SuperStocks - Keith Morel and Hetvi Patel

## Super Stocks is a lightweight application for tracking stocks. Each user will have a dashboard that will provide a brief overview of their chosen stocks. Users will also be able to access more detailed information about indivial stocks.

## Live Demo Running on:  
- http://ec2-54-82-79-227.compute-1.amazonaws.com:8080/SuperStocker 

## Frontend Repository:
- https://github.com/hpatel183/project-2-SuperStocks-Frontend

## Selenium Tests Repository:
- https://github.com/keithmorel/project-2-SeleniumTests

## Documentation Repository:
- https://github.com/hpatel183/project-2-SuperStocks-Documentation

## **Technologies:**
- Spring Framework
- Hibernate
- Angular
- TwelveData API: https://twelvedata.com/docs#getting-started
- Logback
- JUnit
- Selenium
- AWS EC2 Instance
- Jenkins

## Startup Guide
1. Clone the repository:  
```git clone https://github.com/keithmorel/project-2-SuperStocks-Backend.git```

2. Import Project into Spring Tool Suite (or editor of your choice)

3. Update 'src/main/resources/datasource-hibernate.properties' to point to your specific database settings

4. Change 'hibernate.hbm2ddl.auto' to create instead of validate in 'src/main/resources/datasource-hibernate.properties' on your first run:  
```hibernate.hbm2ddl.auto=validate -> hibernate.hbm2ddl.auto=create```

5. Configure Tomcat Server (or server of your choide) to deploy this project and run it once

6. After initial run of project, change 'hibernate.hbm2ddl.auto' back to validate so your database doesn't reset every time:  
```hibernate.hbm2ddl.auto=create -> hibernate.hbm2ddl.auto=validate```

7. Connect to database via DBeaver (or program of your choice)

8. Add 2 rows to 'UserRole' table:  
```INSERT INTO UserRole VALUES (1, "User"), (2, "Admin");```

9. Launch server again
