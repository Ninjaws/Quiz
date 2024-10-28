# Use an official OpenJDK as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y \
    maven=3.3.9-4 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN mvn clean package

CMD ["tail","-f","/dev/null"]