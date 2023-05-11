FROM openjdk:19
VOLUME /tmp
EXPOSE 8761
ADD build/libs/eurekaServer-0.0.1-SNAPSHOT.jar eureka.jar
ENTRYPOINT ["java", "-jar","eureka.jar"]