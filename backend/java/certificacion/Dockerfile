FROM openjdk:8
VOLUME /tmp
EXPOSE 9400
ADD /target/certificacion-0.0.1-SNAPSHOT.jar certificacion.jar
ENTRYPOINT ["java","-jar","/certificacion.jar"]