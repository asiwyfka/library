FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY /target/library-0.0.1-SNAPSHOT.jar /app/library.jar

ENTRYPOINT ["java", "-jar", "library.jar"]