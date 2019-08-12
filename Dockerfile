FROM 12-jdk

ADD target/*.jar /opt/openjdk-12/bottle.jar

CMD ["java -jar bottle.jar"]