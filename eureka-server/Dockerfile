FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

RUN groupadd -r spring && useradd -r -g spring spring
RUN mkdir -p /var/log/app && chown spring:spring /var/log/app

COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar

USER spring

EXPOSE 8761

# ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]