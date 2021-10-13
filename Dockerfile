FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

COPY ./build/libs/bboxx-backend-*.jar /app/service.jar

RUN apk --no-cache add curl

ENTRYPOINT java -server ${JAVA_OPTIONS} -jar service.jar
