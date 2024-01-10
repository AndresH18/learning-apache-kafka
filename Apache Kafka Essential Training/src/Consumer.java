import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class Consumer implements AutoCloseable {
    private final KafkaConsumer<String, String> consumer;
    private final AtomicBoolean canRun;

    public Consumer(Properties properties) {
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(Main.TOPIC));
        canRun = new AtomicBoolean(false);
    }

    public void listenTopic() {
        canRun.set(true);
        while (canRun.get()) {
            System.out.println("Consumer.listenTopic: Polling messages");
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));
            System.out.println("Consumer.listenTopic: messages:");
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("Key = %s , Value = '%s'\n", record.key(), record.value());
            }
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("Calling \"close\" from " + getClass().getName());
        canRun.set(false);
    }
}
