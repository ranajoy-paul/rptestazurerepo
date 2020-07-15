FROM mcr.microsoft.com/java/maven:11-zulu-alpine AS MAVEN_BUILD
# Add Maintainer Info
LABEL maintainer="ranajoy.paul@gmail.com"
# Build the Sprintboot app
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package