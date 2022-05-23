package com.switchfully.evolveandgo.lmsbackend.codelab.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {
    public <T> StudentNotFoundException(T value) {
        super("No student found for: " + value);
    }
}
