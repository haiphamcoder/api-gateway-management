FROM openjdk:17-jdk-slim

WORKDIR /app

COPY .mvn/ .mvn

COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline -B

COPY src ./src

COPY bin ./bin

COPY config ./config

RUN ./mvnw clean install -DskipTests && chmod +x bin/run.sh && \
    addgroup --system --gid 1001 appgroup && \
    adduser --system --uid 1001 appuser && \
    chown -R appuser:appgroup /app

USER appuser

ENTRYPOINT ["/bin/sh", "-c", "bin/run.sh"]
