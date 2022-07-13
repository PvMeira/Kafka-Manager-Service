package com.kenai.br.kafka.admin.service.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private String message;

    private HttpStatus status;

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ApiException(String message, HttpStatus status) {
        super();
        this.message = message;
        this.status = status;
    }

    public ApiException(String message) {
        super();
        this.message = message;
        this.status = HttpStatus.I_AM_A_TEAPOT;
    }

    public ApiException() {
        super();
    }
}