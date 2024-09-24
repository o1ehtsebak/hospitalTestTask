FROM amazoncorretto:17

ENV DB_HOST 'hospital-db'
ENV HOSPITAL_MAIL 'oleg.tsebak16@gmail.com'

COPY build/libs/*.jar app.jar

EXPOSE 8095

ENTRYPOINT ["java", "-jar", "app.jar"]
