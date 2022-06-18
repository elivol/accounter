package com.github.elivol.accounter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ConfirmationTokenNotFoundException extends RuntimeException {

    public ConfirmationTokenNotFoundException(String message) {
        super(message);
    }
}
