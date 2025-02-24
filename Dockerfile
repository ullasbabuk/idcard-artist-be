FROM openjdk:17-slim-buster
RUN mkdir app
COPY target/apiservice-0.0.1-SNAPSHOT.jar app/apiservice-0.0.1-SNAPSHOT.jar
COPY ./application_default_credentials.json app/application_default_credentials.json
WORKDIR app
RUN export GOOGLE_APPLICATION_CREDENTIALS=/app/application_default_credentials.json
ENTRYPOINT ["java", "-jar", "apiservice-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
