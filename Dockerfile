FROM openjdk:11
MAINTAINER joaovitor
COPY build/libs/loan-0.0.1-SNAPSHOT.jar loan-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","loan-0.0.1-SNAPSHOT.jar"]