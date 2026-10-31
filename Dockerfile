# ---------- Stage 1: build (Tasbolatov Temirlan) ----------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy Gradle wrapper and build files first (better layer caching)
COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle
# Fix Windows line endings, make wrapper executable, pre-download dependencies
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew && ./gradlew dependencies --no-daemon > /dev/null

# Copy source code and build the JAR (tests run in Jenkins "Test" stage)
COPY src src
RUN ./gradlew bootJar -x test --no-daemon

# ---------- Stage 2: runtime ----------
FROM eclipse-temurin:17-jre
LABEL maintainer="Tasbolatov Temirlan" \
      project="tasbolatov-temirlan-student-api"
WORKDIR /app

COPY --from=build /app/build/libs/student-api.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
