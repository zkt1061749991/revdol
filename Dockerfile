FROM hub.c.163.com/library/java:8-jre

ADD target/*.jar revdol.jar

EXPOSE 8099

ENTRYPOINT ["java", "-jar", "revdol.jar"]