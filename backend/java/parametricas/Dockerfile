FROM openjdk:11.0.11-9-slim
VOLUME /tmp
EXPOSE 9100
#ARG JAR_FILE=build/libs/parametricas-1.0.0.jar
#ADD ${JAR_FILE} parametricas.jar
ADD .target/parametricas-0.0.1-SNAPSHOT.jar parametricas.jar
ENTRYPOINT ["java","-jar","/parametricas.jar"]