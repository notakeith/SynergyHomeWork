package com.example;

import org.springframework.stereotype.Component;

@Component
public class HelloBean {
    private String message;
    public HelloBean() {
        this.message = "Hello, World!";
    }
    public String getMessage() {
        return message;
    }
}
