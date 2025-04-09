FROM openjdk:21-jdk

WORKDIR /app

COPY build/libs/sandbrick-0.1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
