package com.example.solventum_challenge.exeption;

import lombok.experimental.SuperBuilder;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String message) {
        super(message);
    }
}