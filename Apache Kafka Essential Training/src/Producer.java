import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;


public class Producer implements AutoCloseable {
    private final KafkaProducer<String, String> producer;

    public Producer(Properties properties) {
        System.out.println("This is the producer");
        producer = new KafkaProducer<>(properties);
    }

    public void sendMessage(String message) throws ExecutionException, InterruptedException {
        System.out.println("Message: " + message);
        ProducerRecord<String, String> record = new ProducerRecord<>(Main.TOPIC, message);
        producer.send(record, (recordMetadata, e) -> System.out.println("Message sent"));
    }

    @Override
    public void close() throws Exception {
        System.out.println("Calling \"close\" from " + getClass().getName());
        producer.flush();
        producer.close();
    }
}
