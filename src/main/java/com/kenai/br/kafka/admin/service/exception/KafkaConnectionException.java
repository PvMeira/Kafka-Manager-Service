package com.kenai.br.kafka.admin.service.exception;

import org.springframework.http.HttpStatus;

public class KafkaConnectionException extends ApiException {
    
    public KafkaConnectionException(String message, HttpStatus status) {
        super(message, status);
    }

    public KafkaConnectionException(String message) {
        super(message);
    }
}
