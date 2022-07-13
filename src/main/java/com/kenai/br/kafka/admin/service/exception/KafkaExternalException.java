package com.kenai.br.kafka.admin.service.exception;

import org.springframework.http.HttpStatus;

public class KafkaExternalException extends ApiException {
    public KafkaExternalException(String message, HttpStatus status) {
        super(message, status);
    }

    public KafkaExternalException(String message) {
        super(message);
    }

}
