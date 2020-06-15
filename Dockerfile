#FROM openjdk:14-jdk-alpine
FROM alpine:3.7
RUN echo "1"
COPY server/build/jpackage/server /server
RUN echo "2"
COPY utils/deploys/bootstrap.sh /bootstrap.sh
RUN echo "3"
RUN chmod +x /bootstrap.sh
RUN echo "4"
EXPOSE 80
RUN echo "5"
ENTRYPOINT ["./bootstrap.sh"]
