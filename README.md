## Dependencies
To build

- Java (version 8 or greater)
- Maven

To run

- [Docker](https://www.docker.com/get-started)

## How to Build
- Open a cmd prompt in ./invenio-api
- \> mvn package -DskipTests
- This will build the API and output a jar for docker to pick up

## How to Run
The API jar has been committed so that the app does not need built to run.

- Open a cmd prompt in the project root/invenio and install node modules
  - \> npm install
- Open a cmd prompt in the project root
- \> docker-compose up --build
  - This will build and start the container for the application
    - A container for MongoDB
    - A container for the API (which is a Spring Boot application)
    - A container for the UI (which is a Vue.js application)
  - This will take a while the first time as the base images are downloaded and built
- Navigate to http://localhost:8080/invenio
  - If that doesn't work go to http://192.168.99.100:8080/invenio
- Press ctrl+c in the command prompt to stop the containers

## Troubleshooting
If you are on a Windows machine and see something similar to:
```
ERROR: for mongodb  Cannot start service mongodb: 
driver failed programming external connectivity on endpoint mongodb (69f59f76b92d5e763a28077a207a0f5bb9fcb6e874d272873195934269282ba2): 
Error starting userland proxy: mkdir /port/tcp:0.0.0.0:27017:tcp:172.20.0.2:27017: input/output error
ERROR: Encountered errors while bringing up the project
```
Then restart Docker (right click in task bar -> restart...) and run docker-compose up again. 

## About
This is a social media application with the goal of having the features needed to be used for course management as well.

![Screenshot](https://lh4.googleusercontent.com/oLFLwL5aF5Tj3tmM1W-v2bMs7vE-BAjZdtILtRr52qG794N1pPJqtuDW5Gxlandzu8yZa-iMlIJNpOZQ7M0P3b8MGDUe4fS_4uo7Xf45f3o0Kp8ID_hQci64XQesckgjjA-iYa4N)
