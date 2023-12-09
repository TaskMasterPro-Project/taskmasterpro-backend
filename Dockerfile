# Use an OpenJDK 17 base image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Download the latest release JAR from the GitHub repository
# Replace 'username/repo' with the actual username and repository
# Replace 'app.jar' with the name of the JAR file in the release, if different
ADD https://github.com/TaskMasterPro-Project/taskmasterpro-backend/releases/latest/download/app.jar /app/app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "/app/app.jar"]
