FROM openjdk:14-jdk-alpine
COPY server/build/libs/server-all.jar /app.jar
COPY utils/deploys/bootstrap.sh /bootstrap.sh
RUN chmod +x /bootstrap.sh
EXPOSE 80
ENTRYPOINT ["./bootstrap.sh"]
