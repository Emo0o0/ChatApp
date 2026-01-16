########## BUILD STAGE ##########
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build

# Copy pom first for dependency caching
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn
RUN ./mvnw -B dependency:go-offline

# Copy source and build
COPY src src
RUN ./mvnw -B package -DskipTests


########## RUNTIME STAGE ##########
FROM registry.access.redhat.com/ubi9/openjdk-21:1.23

ENV LANGUAGE='en_US:en'

# Copy the Quarkus app produced by the build stage
COPY --from=build --chown=185 \
    /build/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 \
    /build/target/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 \
    /build/target/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 \
    /build/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185

ENV JAVA_OPTS_APPEND="-Dquarkus.http.host=0.0.0.0 \
 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

ENTRYPOINT ["/opt/jboss/container/java/run/run-java.sh"]
