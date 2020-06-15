#FROM openjdk:14-jdk-alpine
FROM alpine:3.7
COPY server/build/jpackage/server /server
RUN cd /server && ls
COPY utils/deploys/bootstrap.sh /bootstrap.sh
RUN chmod +x /bootstrap.sh
EXPOSE 80
ENTRYPOINT ["./bootstrap.sh"]
