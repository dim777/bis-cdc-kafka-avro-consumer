package ru.techlab.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BisCdcKafkaAvroConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BisCdcKafkaAvroConsumerApplication.class, args);
	}
}
