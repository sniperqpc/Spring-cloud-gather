FROM java:8-jre
MAINTAINER Chiu <sniperqpc@hotmail.com>

ADD ./target/statistics-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/statistics-service.jar"]

EXPOSE 7000