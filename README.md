# resume-portal

## Steps to run the code:

1. Clone the repo

2. Install MySQL

3. Update application.properties file to use the MySQL connection locally. Specifically:

spring.datasource.url, spring.datasource.username and spring.datasource.password

4. Run the main application:

ResumePortalApplication

5. Load page "localhost:8080" to load test data

## Packages used:

### Spring Security
For user login/logout functionality. 



## URLs

1. Login - localhost:{port}/login
2. Load test data - localhost:{port}
3. User profile page - localhost:{port}/view/{userName}
4. User profile edit page - localhost:{port}/edit
