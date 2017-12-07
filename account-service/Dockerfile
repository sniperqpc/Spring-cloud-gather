FROM java:8-jre
MAINTAINER Chiu <sniperqpc@hotmail.com>

ADD ./target/account-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/account-service.jar"]

EXPOSE 6000