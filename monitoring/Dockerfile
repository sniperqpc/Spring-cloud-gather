FROM java:8-jre
MAINTAINER Chiu <sniperqpc@hotmail.com>

ADD ./target/monitoring.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/monitoring.jar"]

EXPOSE 8989 8080