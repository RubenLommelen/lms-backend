package com.switchfully.evolveandgo.lmsbackend.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public <T> UserNotFoundException(T value) {
        super("No student found for: " + value);
    }
}
