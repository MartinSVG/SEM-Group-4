FROM openjdk:latest
COPY ./target/sem-group4-0.1.0.1-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "sem-group4-0.1.0.1-jar-with-dependencies.jar"]