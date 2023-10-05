FROM maven:3.8.6-openjdk-17 AS build
COPY target /home/kotozavr/IdeaProjects/filetrader/target
COPY src /home/kotozavr/IdeaProjects/filetrader/src
COPY pom.xml /home/kotozavr/IdeaProjects/filetrader
RUN mvn -f /home/kotozavr/IdeaProjects/filetrader/pom.xml clean package

FROM gcr.io/distroless/java
COPY --from=build /home/kotozavr/IdeaProjects/filetrader/target/filetrader-0.0.1-SNAPSHOT.jar /usr/app/filetrader-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/filetrader-0.0.1-SNAPSHOT.jar"]
