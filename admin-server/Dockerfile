FROM java:8-jre
MAINTAINER Chiu <sniperqpc@hotmail.com>

ADD ./target/admin-server.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/admin-server.jar"]

EXPOSE 6000