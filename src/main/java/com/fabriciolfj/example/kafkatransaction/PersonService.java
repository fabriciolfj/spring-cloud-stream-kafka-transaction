package com.fabriciolfj.example.kafkatransaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

    private final StreamBridge streamBridge;

    public void execute() {
        streamBridge.send("messageProcessor-out-0", Person.builder()
                .name("Fabricio")
                .document(UUID.randomUUID().toString())
                .build());

        log.info("message send");
    }
}
