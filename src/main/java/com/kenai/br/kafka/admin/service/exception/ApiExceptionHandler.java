package com.kenai.br.kafka.admin.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({ ApiException.class })
    protected ResponseEntity<ApiErrorResponse> handleApiException(ApiException ex) {
        log.error("------------------------------------------------");
        log.error("---------------ApiException---------------");
        log.error("-- Full exception : ", ex);
        log.error("------------------------------------------------");
        return new ResponseEntity<>(  new ApiErrorResponse(  HttpStatus.I_AM_A_TEAPOT
                                                           , ex.getMessage()
                                                           , Instant.now()
                                                           , null)
                                    , HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler({ KafkaConnectionException.class })
    protected ResponseEntity<ApiErrorResponse> handleKafkaConnectionException(KafkaConnectionException ex) {
        log.error("------------------------------------------------");
        log.error("---------------KafkaConnectionException---------------");
        log.error("-- Full exception : ", ex);
        log.error("------------------------------------------------");
        return new ResponseEntity<>(  new ApiErrorResponse(  HttpStatus.INTERNAL_SERVER_ERROR
                                                           , ex.getMessage()
                                                           , Instant.now()
                                                           , null)
                                    , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ KafkaPureException.class })
    protected ResponseEntity<ApiErrorResponse> handleKafkaPureException(KafkaPureException ex) {
        log.error("------------------------------------------------");
        log.error("---------------KafkaPureException---------------");
        log.error("-- Full exception : ", ex);
        log.error("------------------------------------------------");
        return new ResponseEntity<>(  new ApiErrorResponse(  HttpStatus.INTERNAL_SERVER_ERROR
                                                           , ex.getMessage()
                                                           , Instant.now()
                                                           , null)
                                                           , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ WebClientException.class })
    protected ResponseEntity<ApiErrorResponse> handleWebClientException(WebClientException ex) {
        log.error("------------------------------------------------");
        log.error("---------------WebClientException---------------");
        log.error("-- Raw Status Code : " + ex.getRawStatusCode() + " ---");
        log.error("-- Raw Body : " + ex.getRawBody() + " ---");
        log.error("-- Full exception : ", ex);
        log.error("------------------------------------------------");
        return new ResponseEntity<>(  new ApiErrorResponse(  HttpStatus.INTERNAL_SERVER_ERROR
                                                           , ex.getMessage()
                                                           , Instant.now()
                                                           , ex.getRawBody())
                                    , HttpStatus.INTERNAL_SERVER_ERROR);
    }

}