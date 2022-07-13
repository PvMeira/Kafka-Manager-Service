package com.kenai.br.kafka.admin.service.controller.brokers;

import com.kenai.br.kafka.admin.service.configuration.Paths;
import com.kenai.br.kafka.admin.service.controller.DefaultController;
import com.kenai.br.kafka.admin.service.dto.brokers.BrokerConfigResponse;
import com.kenai.br.kafka.admin.service.dto.brokers.BrokersResponse;
import com.kenai.br.kafka.admin.service.dto.log.LogResponse;
import com.kenai.br.kafka.admin.service.service.KafkaBrokerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Log
@RestController
@RequestMapping(Paths.BROKERS_URL_BASE)
public class BrokersController extends DefaultController {

    private final KafkaBrokerService service;

    @Autowired
    public BrokersController(KafkaBrokerService service) {
        this.service = service;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<BrokersResponse> findAll() {
        return new ResponseEntity<>(this.service.getAllBrokers(super.getHeader()), HttpStatus.OK);
    }

    @GetMapping(value = "/{broker_id}/config", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<BrokerConfigResponse>> getConf(@PathVariable("broker_id")String id) {
        return new ResponseEntity<>(this.service.getConf(id, super.getHeader())
                .stream().map(BrokerConfigResponse::parse)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{broker_id}/logs", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<LogResponse>> getLogs(@PathVariable("broker_id")String id) {
        return new ResponseEntity<>(this.service.getLogs(id, super.getHeader()), HttpStatus.OK);
    }
}
