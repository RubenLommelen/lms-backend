package com.switchfully.evolveandgo.lmsbackend.progress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidProgressException extends RuntimeException {
    public InvalidProgressException() {
        super("You can only add a solution when codelab is completed");
    }
}
