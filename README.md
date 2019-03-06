## Prerequisites
- Java
- Maven

Both Java and Maven are only required to build the API. It's possible in the future I will dockerize the build as well so that docker is the only dependency.

- Docker

## How to Run
- Open a cmd prompt in the project root
- \> mvn package -DskipTests
  - This will build the API and output a jar for docker to pick up
- \> docker-compose up --build
  - This will build and start the container for the application
    - A container for MongoDB
    - A container for the api (which is a Spring Boot application)
    - A container for the UI (which is a Vue.js application)
  - This will take a while the first time as the base images are downloaded and built
- Navigate to http://localhost:8080/invenio
  - If that doesn't work go to http://192.168.99.100:8080/invenio
- Press ctrl+c in the command prompt to stop the containers
