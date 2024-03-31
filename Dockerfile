FROM maven:latest AS TEMP_BUILD
ENV DIR=/app
WORKDIR $DIR
COPY . $DIR
USER root
RUN mvn clean package
RUN echo "Build Completed!"

FROM amazoncorretto:20
ENV DIR=/app
ENV JAR_NAME=seller-service-0.0.1-SNAPSHOT.jar
ENV DB_PORT=5432
WORKDIR $DIR
COPY --from=TEMP_BUILD $DIR/target/$JAR_NAME .
EXPOSE 8902
ENTRYPOINT java -jar $JAR_NAME