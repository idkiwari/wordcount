from openjdk:16
add target/libs /opt/libs
add target/json-0.0.1-SNAPSHOT.jar /opt
add static /opt/static
workdir opt
cmd java -jar json-0.0.1-SNAPSHOT.jar
