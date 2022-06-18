package com.github.elivol.accounter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class ConfirmationTokenNotAvailableException extends RuntimeException {

    public ConfirmationTokenNotAvailableException(String message) {
        super(message);
    }
    
}
