# syntax=docker/dockerfile:experimental
FROM maven:3-eclipse-temurin-21 as mvn
COPY src /tmp/src
COPY pom.xml /tmp/pom.xml
WORKDIR /tmp
RUN mvn package -DskipTests

FROM eclipse-temurin:21-alpine
ARG VER=1.0.0
ARG USER=smartera
ARG USER_ID=1005
ARG USER_GROUP=smartera
ARG USER_GROUP_ID=1005
ARG USER_HOME=/home/${USER}
ENV FOLDER=/tmp/target
ENV APP=smartera-engine
ENV VER=${VER}
# create a user group and a user
RUN  addgroup -g ${USER_GROUP_ID} ${USER_GROUP}; \
     adduser -u ${USER_ID} -D -g '' -h ${USER_HOME} -G ${USER_GROUP} ${USER} ;

WORKDIR ${USER_HOME}
COPY --chown=smartera:smartera --from=mvn /tmp/target/${APP}-${VER}.jar ${USER_HOME}
USER 1005
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar ${APP}-${VER}.jar"]
