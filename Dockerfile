FROM openjdk:8-jdk-alpine

ADD target/app.jar /app.jar

ENV JAVA_OPTS=""
ENV RUN_OPTS=""

ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${RUN_OPTS}" ]
