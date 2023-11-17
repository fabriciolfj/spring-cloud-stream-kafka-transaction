package com.fabriciolfj.example.kafkatransaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class BeansConsumerConfig {

    @Bean
    public Consumer<String> messageProcessor() {
        return v -> log.info("receive message {}", v);
    }
}
