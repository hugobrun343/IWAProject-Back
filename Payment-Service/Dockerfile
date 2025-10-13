FROM eclipse-temurin:21-jdk AS build

WORKDIR /workspace

# Copy parent POM
COPY pom.xml ./

# Copy service files
COPY Payment-Service ./Payment-Service/

# Build
WORKDIR /workspace/Payment-Service
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests package

# Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/Payment-Service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
