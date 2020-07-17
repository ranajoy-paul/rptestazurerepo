FROM maven:3.6.3-jdk-11-openj9 as MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
COPY manifest /build/manifest/
WORKDIR /build/
RUN mvn clean package -DskipTests

FROM openjdk:11
ARG JAR_FILE
ADD ${JAR_FILE} portcallopslegacy-0.0.1-SNAPSHOT.jar
RUN mkdir -p /home/AzureUser/portcallopslegacy/bin
RUN mkdir -p /home/AzureUser/portcallopslegacy/xmldata
RUN mkdir -p /home/AzureUser/portcallopslegacy/manifest
COPY --from=MAVEN_BUILD /build/src/main/resources/Data/* /home/AzureUser/portcallopslegacy/xmldata/
COPY --from=MAVEN_BUILD /build/manifest/* /home/AzureUser/portcallopslegacy/manifest
COPY --from=MAVEN_BUILD /build/target/portcallopslegacy-0.0.1-SNAPSHOT.jar /home/AzureUser/portcallopslegacy/bin/
WORKDIR /home/AzureUser/portcallopslegacy/bin
ENTRYPOINT ["java","-Dserver.port=8081", "--data.path=file:/home/AzureUser/portcallopslegacy/xmldata/","portcallopslegacy-0.0.1-SNAPSHOT.jar"]
