FROM mirror.gcr.io/library/eclipse-temurin:17-jdk

WORKDIR /app
COPY target/*.jar wms.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "wms.jar"]