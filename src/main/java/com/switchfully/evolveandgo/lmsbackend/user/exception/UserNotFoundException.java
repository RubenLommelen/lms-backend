package com.switchfully.evolveandgo.lmsbackend.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public <T> UserNotFoundException(T value) {
        super("No user found for: " + value);
    }
}
