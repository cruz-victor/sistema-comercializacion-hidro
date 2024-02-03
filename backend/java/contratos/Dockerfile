FROM openjdk:8
VOLUME /tmp
EXPOSE 9200
ADD /target/contratos-0.0.1-SNAPSHOT.jar contratos.jar
ENTRYPOINT ["java","-jar","/contratos.jar"]