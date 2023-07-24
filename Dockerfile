FROM eclipse-temurin:20-jdk AS build
FROM eclipse-temurin:20-jre
ARG PORT
ENV PORT=${PORT}
COPY --from=target /app/app.jar .
RUN useradd runtime
USER runtime
ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-jar", "app.jar" ]