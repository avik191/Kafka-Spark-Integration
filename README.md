# Kafka-Spark-Integration
Sample spark codes with apache kafka integration </b></b>

# Running Kafka and Zookeeper</b></b>
https://dzone.com/articles/running-apache-kafka-on-windows-os

1. run zookeeper server -> zkserver
2. run kafka server -> .\bin\windows\kafka-server-start.bat .\config\server.properties
3. creating a topic -> (inside \bin\windows) .\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic testLogs
4. creating producer -> kafka-console-producer.bat --broker-list localhost:9092 --topic test2
5. creating consumer -> kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test2

for running multiple kafka servers in local system create copies of server.properties file and change port of each and change the log.dir
for running kafka server in the cluster, in the server.properties file , change zookeeper.connect = ip:port where ip = ip of cluster or system where zookeeper is running.
