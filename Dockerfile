FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/*.jar /app/app.jar

COPY src/main/resources/application.yaml /app/config/application.yaml
ENV SPRING_CONFIG_LOCATION=file:/app/config/application.yaml
EXPOSE 8801

CMD ["java", "-jar", "/app/app.jar"]