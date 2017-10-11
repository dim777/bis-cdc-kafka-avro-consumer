package ru.techlab.kafka.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.techlab.kafka.component.AvroDeserializer;
import ru.techlab.kafka.component.Receiver;
import ru.techlab.kafka.model.customer.AvroCustomer;
import ru.techlab.kafka.model.customer.JsonCustomer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitry.Erohin dim777@ya.ru on 18.07.2017.
 * Copyright (C) 2017 - present by <a href="https://www.ineb.ru/">Ineb Inc</a>.
 * Please see distribution for license.
 */
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "cdc");
        return props;
    }


    @Bean
    public ConsumerFactory<String, JsonCustomer> consumerFactory() {
        /*return new DefaultKafkaConsumerFactory(
                consumerConfigs(),
                new StringDeserializer(),
                new DefaultAvroDeserializer(BaseTransaction.class));*/

        //return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                new StringDeserializer(),
                new JsonDeserializer<>(JsonCustomer.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JsonCustomer> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JsonCustomer> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }
}
