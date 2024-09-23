FROM amazoncorretto:17

COPY build/libs/*.jar app.jar

EXPOSE 8095

ENTRYPOINT ["java", "-jar", "app.jar"]
