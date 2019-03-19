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

![Screenshot](https://d1b10bmlvqabco.cloudfront.net/attach/jqfcudg7qwo8a/iyqq20ov61z72b/jt3q3ovhcr73/screenshot.png)
