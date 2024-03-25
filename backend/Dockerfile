FROM maven:3.9.6-eclipse-temurin-21-alpine as build
RUN mkdir /src
COPY . /src
WORKDIR /src
RUN mvn clean package -DskipTests -Dquarkus.container-image.build=true


FROM registry.access.redhat.com/ubi8/openjdk-21:1.18

COPY --chown=185 --from=build /src/target/quarkus-app/lib/ /deployments/lib/
COPY --chown=185 --from=build /src/target/quarkus-app/*.jar /deployments/
COPY --chown=185 --from=build /src/target/quarkus-app/app/ /deployments/app/
COPY --chown=185 --from=build /src/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185
ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh" ]

