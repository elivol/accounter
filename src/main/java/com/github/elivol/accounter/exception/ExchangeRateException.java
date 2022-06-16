package com.github.elivol.accounter.exception;

public class ExchangeRateException extends RuntimeException{

    private final String errorType;

    public String getErrorType() {
        return errorType;
    }

    public ExchangeRateException(String message, String errorType) {
        super(message);
        this.errorType = errorType;
    }

}
