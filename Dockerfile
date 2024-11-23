# Build stage
FROM gradle:7.6.0-jdk17 AS build

WORKDIR /app

# 필요한 파일 복사
COPY . .

# Gradle Wrapper 실행 권한 부여
RUN chmod +x gradlew

# 빌드 수행
RUN ./gradlew build -x test --info

# Run stage
FROM openjdk:11-jre-slim

WORKDIR /app

# 빌드 결과 복사
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
