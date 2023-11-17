package com.fabriciolfj.example.kafkatransaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    private String name;
    private String document;
}
