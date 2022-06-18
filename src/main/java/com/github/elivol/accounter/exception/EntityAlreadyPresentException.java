package com.github.elivol.accounter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityAlreadyPresentException extends RuntimeException {

    public EntityAlreadyPresentException(String message) {
        super(message);
    }
}
