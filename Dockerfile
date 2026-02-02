FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Gradle Wrapper 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# 실행 권한
RUN chmod +x gradlew

# 의존성 캐시
RUN ./gradlew dependencies --no-daemon || true

# 소스 복사
COPY src src

# 빌드
RUN ./gradlew clean build -x test --no-daemon

# 실행 (버전 안전)
CMD ["sh", "-c", "java -jar build/libs/*.jar"]
