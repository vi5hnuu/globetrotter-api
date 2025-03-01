FROM openjdk:17

# Set the working directory inside the container
WORKDIR /globetrotter-api

COPY mvnw.cmd .
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# give execute permisson to mvnw on linux
RUN chmod +x mvnw

# Download the dependencies
RUN ./mvnw dependency:go-offline

# Copy the application source code
COPY src src

# Build the application
RUN ./mvnw package -DskipTests

EXPOSE 9999

# Specify the command to run your application
CMD ["java", "-jar", "target/globetrotter-api.jar"]

