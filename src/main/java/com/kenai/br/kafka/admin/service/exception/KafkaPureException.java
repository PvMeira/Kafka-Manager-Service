package com.kenai.br.kafka.admin.service.exception;

import org.springframework.http.HttpStatus;

public class KafkaPureException extends ApiException {

    public KafkaPureException(String message) {
        super(message, HttpStatus.I_AM_A_TEAPOT);

    }
}
