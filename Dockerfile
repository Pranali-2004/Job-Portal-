FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only the POM first so dependency downloads are cached in their own Docker layer
COPY pom.xml .
RUN mvn -B -Dmaven.wagon.http.retryHandler.count=3 \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=25 \
    dependency:go-offline

# Now copy the actual source code and build
COPY src ./src
RUN mvn -B -Dmaven.wagon.http.retryHandler.count=3 clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]