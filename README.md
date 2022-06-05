<p align="center">
  <a href="https://github.com/NityanandaBarbosa/Classroom-SpringBoot" target="blank"><img src="https://seeklogo.com/images/S/spring-logo-9A2BC78AAF-seeklogo.com.png" width="200" alt="Java Logo" /></a>
</p>

## Description

Tutorial to TJW - IFCE Maracanau


## Requirements
- Java installed
- MySQL installed

## Installation

```bash
# paste this in your terminal
$ git clone git@github.com:NityanandaBarbosa/Classroom-SpringBoot.git
```

## How to execute
### 1 - Importing the project

With eclipse pre installed click on "file" and go to the "import" option, choose the "existing maven projects" option, a pop-up will appear on your screen and in the field root directory click on "browser" and choose the project and click "finish".

### 2 - Install/Update dependencies

Click with right click on Classroom-SpringBoot folder, move your mouse to "Maven" then click on "Update Project", a pop-up will appear on your screen, then you must select the project and check the option 'Force Update of Snapshots/Releases',after these click 'Ok'.

### 3 - Database

Once again, taking into account that the Mysql database is already installed, you must open the application.properties, then change "spring.datasource.username" and "spring.datasource.password" according your MySQL credentials.

### 4 - Running the code

Basically what you should do now is open src/main/java/ifce/tjw/spring/Application.java and look for the package called clients, you'll see a file named as Application.java then you click with right click, move your mouse to "Run As" then click on "Java application".

### 5 - Test endpoints

#### [Download](https://drive.google.com/file/d/1xytiY8Ota8DeAO9IqS-n9WoKY9zIvWuB/view?usp=sharing) our insomnia collection then test the endpoints
##### Endpoints that you are allowed to request without a jwt token :
    - /auth/sing-up
    - /login

- Execute /auth/sing-up to create your user./n
- Execute /login with your email and password, then save your access_token. 
- To execute another endpoint o must use Bearer Authentication sending access_token. 

## Stay in touch

- Author - Nityananda Barbosa