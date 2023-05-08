FROM openjdk:19
VOLUME /tmp
EXPOSE 8080
ADD build/libs/schedule-service-0.0.1-SNAPSHOT.jar schedule-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","schedule-service-0.0.1-SNAPSHOT.jar"]