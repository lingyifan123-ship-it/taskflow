FROM maven:3.9.11-eclipse-temurin-26 AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn -q -DskipTests package
FROM eclipse-temurin:26-jre
WORKDIR /app
COPY --from=build /app/target/taskflow-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
