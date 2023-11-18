package com.fabriciolfj.example.kafkatransaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class BeansConsumerConfig {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public Consumer<String> messageProcessor() {
        return v -> {
            log.info("receive message {}", v);
            try {
                save(v);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Transactional
    public void save(String json) throws JsonProcessingException {
        var person = objectMapper.readValue(json, Person.class);
        var entity = new PersonEntity(null, person.getName(), person.getDocument());

        repository.save(entity);
        log.info("person save success");
    }
}
