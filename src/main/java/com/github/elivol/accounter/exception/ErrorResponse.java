package com.github.elivol.accounter.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    public final LocalDateTime timestamp;
    public final String status;
    public final String error;
    public final String message;
    public final String path;

    public ErrorResponse(String status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
