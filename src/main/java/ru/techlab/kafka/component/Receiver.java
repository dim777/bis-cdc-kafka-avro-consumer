package ru.techlab.kafka.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import ru.techlab.kafka.model.customer.AvroCustomer;

import java.util.concurrent.CountDownLatch;

/**
 * Created by rb052775 on 02.08.2017.
 */
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "${kafka.topics.customer}")
    public void receiveCustomer(AvroCustomer customer) {
        //LOGGER.info("received payload='{}'", payload);
        LOGGER.info("received payload='{}'", customer.toString());
        latch.countDown();
    }

    /*@KafkaListener(topics = "${kafka.topics.transaction}")
    public void receiveAvroTransaction(BaseTransaction transaction) {
        LOGGER.info("received payload='{}'", transaction);
        latch.countDown();
    }*/

    /*@KafkaListener(topics = "${kafka.topics.transaction}")
    public void receiveCustomer2(String payload) {
        LOGGER.info("received payload='{}'", payload);
        latch.countDown();
    }*/
}
