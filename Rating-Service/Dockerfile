FROM eclipse-temurin:21-jdk AS build

WORKDIR /workspace

# Copy parent POM
COPY pom.xml ./

# Copy service files
COPY Rating-Service ./Rating-Service/

# Build
WORKDIR /workspace/Rating-Service
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests package

# Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/Rating-Service/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
