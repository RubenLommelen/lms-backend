package com.switchfully.evolveandgo.lmsbackend.progress.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoProgressException extends RuntimeException {
    public NoProgressException() {
        super("No progression found for this student");
    }
}
