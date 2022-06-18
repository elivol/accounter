package com.github.elivol.accounter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CurrencyIsNotSupportedException extends RuntimeException {

    public CurrencyIsNotSupportedException(String message) {
        super(message);
    }
}
