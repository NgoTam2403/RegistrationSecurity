FROM eclipse-temurin:20-jdk AS build


ARG PORT
ENV PORT=${PORT}
ENTRYPOINT [ "java", "-Dserver.port=${PORT}", "-jar", "target/*jar" ]