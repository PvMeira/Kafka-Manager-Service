package com.kenai.br.kafka.admin.service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class WebClientException extends ApiException {
    @Getter
    private int rawStatusCode;
    @Getter
    private String rawBody;

    public WebClientException(String body, int status, String message) {
        super(message, HttpStatus.I_AM_A_TEAPOT);
        this.rawBody = body;
        this.rawStatusCode = status;
    }
    public WebClientException(String message, HttpStatus status) {
        super(message, status);
    }

    public WebClientException(String message) {
        super(message);
    }
}
