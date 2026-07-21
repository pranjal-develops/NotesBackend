# -------- Build stage --------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

# Build the fat jar
RUN mvn -q clean package -DskipTests

# -------- Run stage --------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Your expected output jar name:
# target/note-0.0.1-SNAPSHOT.jar
COPY --from=build /app/target/note-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
