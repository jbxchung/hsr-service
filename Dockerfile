FROM eclipse-temurin:17-jre

WORKDIR /opt/hsr

COPY target/hsr-service-1.0.0.jar /opt/hsr/hsr-service-1.0.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_ENV}", "-jar", "hsr-service-1.0.0.jar"]
#ENTRYPOINT sleep 99999999999999999