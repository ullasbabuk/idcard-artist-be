#FROM openjdk:17-slim-buster
FROM maven:3.8-jdk-11
RUN mkdir artist
RUN cd artist
COPY *.* .
RUN mvn -B package
#
RUN mkdir app
COPY target/apiservice-0.0.1-SNAPSHOT.jar app/apiservice-0.0.1-SNAPSHOT.jar
COPY application_default_credentials.json app/application_default_credentials.json
WORKDIR app
RUN EXPORT GOOGLE_APPLICATION_CREDENTIALS=app/application_default_credentials.json
ENTRYPOINT ["java", "-jar", "apiservice-0.0.1-SNAPSHOT.jar"]
