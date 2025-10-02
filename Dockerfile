FROM eclipse-temurin:17-jre

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS="-Dhttps.protocols=TLSv1,TLSv1.1,TLSv1.2"

COPY target/Booking-Room-Service-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
