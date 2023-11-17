package com.fabriciolfj.example.kafkatransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.DefaultBinderFactory;
import org.springframework.cloud.stream.binder.kafka.KafkaMessageChannelBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ConfigBeans {

    @Autowired
    DefaultBinderFactory binderFactory;

    @Bean
    KafkaTransactionManager customKafkaTransactionManager() {
        KafkaMessageChannelBinder kafka = (KafkaMessageChannelBinder)this.binderFactory.getBinder("kafka", MessageChannel.class);
        ProducerFactory<byte[], byte[]> transactionalProducerFactory = kafka.getTransactionalProducerFactory();
        KafkaTransactionManager kafkaTransactionManager = new KafkaTransactionManager(transactionalProducerFactory);
        return kafkaTransactionManager;
    }



}
