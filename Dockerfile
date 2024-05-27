# temp build
FROM docker.io/gradle:8.5.0 AS TEMP_BUILD
ARG SKIP_TESTS=false
COPY build.gradle settings.gradle /home/gradle/src/
COPY src /home/gradle/src/src
COPY config /home/gradle/src/config
COPY config/monitoring /home/gradle/src/monitoring
COPY docs /home/gradle/src/docs
COPY gradle /home/gradle/src/gradle
WORKDIR /home/gradle/src
RUN if [ "$SKIP_TESTS" = "true" ]; then \
    gradle build --no-daemon -x test; \
  else \
    gradle build --no-daemon; \
  fi

# build image
FROM openjdk:17-alpine
RUN addgroup -S nonroot \
    && adduser -S nonroot -G nonroot
USER nonroot
WORKDIR /app
COPY --from=TEMP_BUILD /home/gradle/src/build/libs/*.jar /app/wallet-api.jar
ENTRYPOINT ["java", "-jar", "/app/wallet-api.jar"]