# Apache Kafka Essential Learning

Learning Apache Kafka with project

## Docker compose

Will use docker compose for _Kafka_ and the required dependency _Zookeeper_

[docker-compose file](docker-compose.yaml)

## Exercise

### Instructions

1. Create a **kafka.usecase.students** topic
    - Replication Factor=1
    - Partitions=2

2. Publish messages to the topic using console producer. Consume them using the console consumer.
3. Create a java Producer and Consumer for topic.

### Results

1. topic
   ```shell
   # Create topic
   kafka-topics --bootstrap-server localhost:9092 --create --topic kafka.usecase.students --partitions 2 --replication-factor 1
   
   # Describe
   kafka-topics --bootstrap-server localhost:9092 --topic kafka.usecase.students --describe
   ```

2. Publish/Consume
   ```shell
   # produce - enter this, enter, and add message, end by ctrl + C
   kafka-console-producer --bootstrap-server localhost:9092 --topic kafka.usecase.students
   
   # consume messages
   kafka-console-consumer --bootstrap-server localhost:9092 --topic kafka.usecase.students
   
   # consume messages from the beginning
   kafka-console-consumer --bootstrap-server localhost:9092 --topic kafka.usecase.students --from-beginning
   ```
3. - [Producer](src/Producer.java)  
   - [Consumer](src/Consumer.java)
