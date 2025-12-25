FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . /app
RUN javac Options.java TaskServices.java Task.java
CMD ["java", "Options"]