FROM eclipse-temurin:17-jdk
WORKDIR /usr/local/app
COPY target/exploration-0.0.1-SNAPSHOT.jar exploration-0.0.1-SNAPSHOT.jar
RUN apt-get update && \
    apt-get install -y --no-install-recommends curl
EXPOSE 8887
ENTRYPOINT ["java", "-jar", "exploration-0.0.1-SNAPSHOT.jar"]
HEALTHCHECK --interval=5s --timeout=5s --start-period=30s --retries=5 \
  CMD curl -f http://localhost:8888/actuator/health || exit 1
