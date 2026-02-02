FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Gradle Wrapper 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

# 의존성 캐시
RUN ./gradlew dependencies || true

# 소스 복사
COPY src src

# 빌드
RUN ./gradlew clean build -x test

# 실행
CMD ["java", "-jar", "build/libs/shortify-crawler.jar"]
