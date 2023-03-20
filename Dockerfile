FROM openjdk:latest
COPY ./target/semGroup4.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "semGroup4.jar", "db:3306", "30000"]