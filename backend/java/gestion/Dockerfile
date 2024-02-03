FROM openjdk:8
VOLUME /tmp
EXPOSE 9300
ADD /target/gestion-0.0.1-SNAPSHOT.jar gestion.jar
ENTRYPOINT ["java","-jar","/gestion.jar"]