# ==========================================
# Stage 1: Build stage with Maven & OpenJDK 17
# ==========================================
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /app

# Copy Maven wrapper configuration and dependencies descriptor
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Fix Windows CRLF line endings on wrapper script and set executable permissions
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Pre-fetch dependencies to leverage Docker layer caching
RUN ./mvnw dependency:go-offline -B || true

# Copy project source code
COPY src ./src

# Build production JAR package skipping tests
RUN ./mvnw clean package -DskipTests

# ==========================================
# Stage 2: Minimal Production Runtime Image
# ==========================================
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create a non-privileged user for security compliance
RUN groupadd -r hrmsgroup && useradd -r -g hrmsgroup hrmsuser

# Copy the built jar from the builder stage
COPY --from=builder /app/target/hrms-*.jar app.jar
RUN chown -R hrmsuser:hrmsgroup /app

USER hrmsuser:hrmsgroup

# Default server port
ENV PORT=8181
EXPOSE 8181

# Optimize JVM startup and memory
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
