# FROM eclipse-temurin:17-jdk-focal
 
# WORKDIR /app
 
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN sed -i 's/\r$//' mvnw
# RUN ./mvnw dependency:resolve
 
# COPY src ./src
 
# CMD ["./mvnw", "spring-boot:run"]

#
# Build stage
#
FROM maven:3.8.1-openjdk-11-slim AS build
COPY src  /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:11-jdk
COPY --from=build /home/app/target/*.jar /usr/local/lib/app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
