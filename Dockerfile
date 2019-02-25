FROM java:8
WORKDIR /
ADD /target/invenio-0.0.1-SNAPSHOT.jar invenio-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD java -jar invenio-0.0.1-SNAPSHOT.jar
