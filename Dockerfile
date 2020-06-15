#FROM openjdk:14-jdk-alpine
FROM alpine:3.7
#FROM ubuntu:latest
COPY server/build/jpackage/server /server
COPY utils/deploys/bootstrap.sh /bootstrap.sh
RUN chmod +x /bootstrap.sh
EXPOSE 80
ENTRYPOINT ["./bootstrap.sh"]
