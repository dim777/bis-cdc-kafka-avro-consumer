package ru.techlab.kafka.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by rb052775 on 02.08.2017.
 */
@Configuration
@EnableAsync
@EnableKafka
public class AppConfig {
}
