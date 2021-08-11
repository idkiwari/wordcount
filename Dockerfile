from openjdk:16
add target/libs /opt/libs
add target/wordcount-0.0.1-SNAPSHOT.jar /opt
add static /opt/static
workdir opt
cmd java -jar wordcount-0.0.1-SNAPSHOT.jar
