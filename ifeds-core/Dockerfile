FROM maven:3.5-jdk-8-alpine as build

WORKDIR /app
COPY ./pom.xml /app
RUN mvn verify clean --fail-never

# Image layer: with the application
COPY . /app
RUN mvn clean install -DskipTests

FROM openjdk:8-alpine
WORKDIR /app
COPY --from=build app/target/ifeds-core-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
#ADD ./target/ifeds-core-0.0.1-SNAPSHOT.jar spring-boot-docker.jar
ENTRYPOINT ["java","-jar","ifeds-core-0.0.1-SNAPSHOT.jar"]
