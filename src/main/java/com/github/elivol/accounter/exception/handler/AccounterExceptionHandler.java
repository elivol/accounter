package com.github.elivol.accounter.exception.handler;

import com.github.elivol.accounter.exception.ErrorResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AccounterExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponse defaultHandler(HttpServletRequest request, Exception ex) throws Exception {

        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
            throw ex;
        }

        return new ErrorResponse(
                Integer.toString(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValid(HttpServletRequest request, MethodArgumentNotValidException ex) {

        StringBuilder message = new StringBuilder("Validation failed for: ");

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        String details = errors.stream()
                .map(e -> e.getField() + ", rejected value \"" +
                        (Objects.isNull(e.getRejectedValue()) ? "null" : e.getRejectedValue()) + "\"" + " (" +
                        e.getDefaultMessage() + ")"
                )
                .collect(Collectors.joining("; "));

        message.append(details);

        return new ErrorResponse(
                Integer.toString(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message.toString(),
                request.getRequestURI());
    }

}
