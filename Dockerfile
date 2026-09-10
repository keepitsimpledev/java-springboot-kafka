FROM eclipse-temurin:17-jdk
COPY target/exploration-0.0.1-SNAPSHOT.jar /exploration-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "exploration-0.0.1-SNAPSHOT.jar"]