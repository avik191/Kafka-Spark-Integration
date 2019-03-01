# Kafka-Spark-Integration
Sample spark codes with apache kafka integration </b></b>


Apache Kafka is an open-source stream-processing software platform.
Apache Kafka is based on the commit log, and it allows users to subscribe to it and publish data to any number of systems or real-time applications.
Kafka stores key-value messages that come from arbitrarily many processes called producers. The data can be partitioned into different "partitions" within different "topics". Within a partition, messages are strictly ordered by their offsets (the position of a message within a partition), and indexed and stored together with a timestamp. Other processes called "consumers" can read messages from partitions. For stream processing, Kafka offers the Streams API that allows writing Java applications that consume data from Kafka and write results back to Kafka. Apache Kafka also works with external stream processing systems such as Apache Apex, Apache Flink, Apache Spark, and Apache Storm.</b></b>

# Running Kafka and Zookeeper</b></b>
https://dzone.com/articles/running-apache-kafka-on-windows-os

1. run zookeeper server -> zkserver
2. run kafka server -> .\bin\windows\kafka-server-start.bat .\config\server.properties
3. creating a topic -> (inside \bin\windows) .\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic testLogs
4. creating producer -> kafka-console-producer.bat --broker-list localhost:9092 --topic test2
5. creating consumer -> kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test2

for running multiple kafka servers in local system create copies of server.properties file and change port of each and change the log.dir</b>
for running kafka server in the cluster, in the server.properties file , change zookeeper.connect = ip:port where ip = ip of cluster or system where zookeeper is running.

<b> -- 1. kafka_word_count.scala - Does word count of files streamed through 2 kafka servers. --</b>
