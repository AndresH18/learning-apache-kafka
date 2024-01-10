import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static final String TOPIC = "kafka.usecase.students";

    public static void main(String[] args) {
        System.out.println("Application started");

        String bootstrapServers = "localhost:9092";

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // It is a type of Serializer class of the key that is used to implement the org.apache.kafka.common.serialization.Serializer interface.
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // It is a type of Serializer class which implements the org.apache.kafka.common.serialization.Serializer interface.
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // It is a Deserializer class for the key, which is used to implement the org.apache.kafka.common.serialization.Deserializer interface.
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // A Deserializer class for value which implements the org.apache.kafka.common.serialization.Deserializer interface.
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // It is a unique string that identifies the consumer of a consumer group.
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-test-consumer");
        // This property is required when no initial offset is present or if the current offset does not exist anymore on the server. There are the following values used to reset the offset values:
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        String message;
        try (
                Producer producer = new Producer(properties);
                Consumer consumer = new Consumer(properties);
                ExecutorService consumerExecutor = Executors.newSingleThreadExecutor();
                Scanner sc = new Scanner(System.in)
        ) {
            consumerExecutor.execute(consumer::listenTopic);
            while (true) {
                System.out.println("Ingresa un mensaje");
                message = sc.nextLine();
                if (message == null || message.isBlank())
                    break;
                producer.sendMessage(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(Integer.MIN_VALUE);
        }
        System.out.println("Goodbye");
    }
}