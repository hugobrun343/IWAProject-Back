FROM eclipse-temurin:21-jdk AS build

WORKDIR /workspace/app

# Copy Maven wrapper and pom from service directory
COPY Payment-Service/mvnw .
COPY Payment-Service/.mvn .mvn
COPY Payment-Service/pom.xml .

# Copy source code
COPY Payment-Service/src src

# Build application
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy jar from build stage
COPY --from=build /workspace/app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
