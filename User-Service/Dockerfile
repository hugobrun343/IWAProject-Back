FROM eclipse-temurin:21-jdk AS build

WORKDIR /workspace/app

# Copy Maven wrapper and pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy source code
COPY src src

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
