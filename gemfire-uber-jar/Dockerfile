FROM openjdk:8

ARG JAR_FILE=target/gemfire-1.0-jar-with-dependencies.jar
ARG SCRIPT_FILE=./run.sh
ARG WORKING_DIR=/apps/gemfire

RUN mkdir -p $WORKING_DIR
RUN mkdir -p /apps/gemfire/build
COPY $JAR_FILE $WORKING_DIR/gemfire-app.jar
COPY $SCRIPT_FILE $WORKING_DIR

EXPOSE 10344
ENTRYPOINT ["/bin/bash", "/apps/gemfire/run.sh"]