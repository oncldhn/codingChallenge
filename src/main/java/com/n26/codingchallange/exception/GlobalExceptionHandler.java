package com.n26.codingchallange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidJsonException.class)
    public void handleInvalidJsonException(InvalidJsonException e) {

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(TransactionExpiredException.class)
    public void handleIInvalidTransactionDateException(TransactionExpiredException e) {

    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(JSONParseException.class)
    public void handleJSONParseException(JSONParseException e) {

    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(TransactionDateIsInFutureException.class)
    public void handleTransactionDateFutureException(TransactionDateIsInFutureException e) {

    }

}
