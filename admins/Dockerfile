FROM openjdk:19
VOLUME /tmp
EXPOSE 8095
ADD build/libs/admins-0.0.1-SNAPSHOT.jar admins.jar
ENTRYPOINT ["java", "-jar","admins.jar"]