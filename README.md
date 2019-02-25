## How to Run
1. Open a cmd prompt in this location
2. \> mvn package
3. \> docker build --file=Dockerfile.server --tag=invenio:latest --rm=true
4. \> docker run --name=invenio --publish=8080:8080 invenio:latest
5. Navigate to http://192.168.99.100:8080/invenio

You should be redirected to the swagger UI which is documentation for the API. When the application has a homepage I will update this documentation.