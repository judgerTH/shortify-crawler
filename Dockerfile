FROM eclipse-temurin:17-jdk-jammy

# =========================
# Timezone
# =========================
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app

# =========================
# Gradle Wrapper
# =========================
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew

# =========================
# Dependency Cache
# =========================
RUN ./gradlew dependencies --no-daemon || true

# =========================
# Source
# =========================
COPY src src

# =========================
# Build
# =========================
RUN ./gradlew bootJar --no-daemon

# =========================
# Run
# =========================
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/shortify-crawler-0.0.1-SNAPSHOT.jar"]

