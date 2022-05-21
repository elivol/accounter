# About project
This is an example REST service for managing users accounts.</br>
It allows to register, create/read/update/delete accounts and operations under specific account of logged in user.

# Technology stack
- Java
- Spring Framework
  - Spring Boot
  - Spring Data
  - Spring Security
  - Spring HATEOAS
- PostgreSQL

# How to run
For successful running you need postgres running at localhost:5432 and mail server running at localhost:1025.</br>
For postgres I use [official docker image](https://hub.docker.com/_/postgres).</br>
As mail server I use [maildev docker image](https://hub.docker.com/r/maildev/maildev)

# How to use
First, you need to reigister (GET method /register), confirm your email and log in to system.</br>
Then you can start from GET method /me, this will return your username, email and fullname.</br>
From /me you can go to 
- /accounts 
- /operations 
- /accounts/{id} 
- /accounts/{id}/operations/ 
- /accounts/{id}/operations/{id}</br>

and perform CRUD operations with this account and operation entities.
