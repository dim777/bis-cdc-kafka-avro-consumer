package ru.techlab.kafka.testConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import ru.techlab.kafka.model.customer.AvroCustomer;

/**
 * Created by dim777999 on 11.10.2017.
 */
public class Sender {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Value("${kafka.topics.customer}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, AvroCustomer> kafkaTemplate;

    public void send(AvroCustomer customer) {
        LOGGER.info("sending user='{}'", customer.toString());
        kafkaTemplate.send(topic, customer);
    }
}
