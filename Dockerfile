FROM openjdk:12-jdk

ADD target/bottle-1.0-SNAPSHOT-jar-with-dependencies.jar bottle.jar

CMD ["java","-jar","bottle.jar"]