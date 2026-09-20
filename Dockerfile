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

# Create a non-privileged user and data directory
RUN groupadd -r hrmsgroup && useradd -r -g hrmsgroup hrmsuser \
    && mkdir -p /app/data \
    && chown -R hrmsuser:hrmsgroup /app

# Copy the built jar from the builder stage
COPY --from=builder /app/target/hrms-*.jar app.jar
RUN chown hrmsuser:hrmsgroup app.jar

USER hrmsuser:hrmsgroup

# Cloud environment settings
ENV PORT=8181
ENV DB_URL=jdbc:h2:file:/app/data/hrmsdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1
ENV DB_DRIVER=org.h2.Driver
ENV DB_USERNAME=sa
ENV DB_PASSWORD=

EXPOSE 8181

# Optimize JVM startup and limit memory for 512MB free tier containers
ENTRYPOINT ["java", "-Xmx380m", "-Xms128m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
