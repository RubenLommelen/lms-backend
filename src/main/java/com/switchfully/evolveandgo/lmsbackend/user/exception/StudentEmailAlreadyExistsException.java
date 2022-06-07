package com.switchfully.evolveandgo.lmsbackend.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentEmailAlreadyExistsException extends RuntimeException {
    public StudentEmailAlreadyExistsException(String email) {
        super("Email already exists, for email : " + email);
    }
}
