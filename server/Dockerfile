FROM openjdk:17
ARG IDLE_PROFILE
ARG JAR_FILE=*.jar
ENV ENV_IDLE_PROFILE=$IDLE_PROFILE
COPY ${JAR_FILE} app.jar
RUN echo $ENV_IDLE_PROFILE
ENTRYPOINT ["java", "-Dspring.profiles.active=${ENV_IDLE_PROFILE}", "-jar","/app.jar"]