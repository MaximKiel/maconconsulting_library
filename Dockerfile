FROM maven:3.9.8-eclipse-temurin AS builder
WORKDIR /app
COPY ./pom.xml /app/pom.xml
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip=true

FROM builder
WORKDIR /app
COPY --from=builder /app/target/library-0.0.1-SNAPSHOT.jar /app/library-0.0.1-SNAPSHOT.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app/library-0.0.1-SNAPSHOT.jar"]
