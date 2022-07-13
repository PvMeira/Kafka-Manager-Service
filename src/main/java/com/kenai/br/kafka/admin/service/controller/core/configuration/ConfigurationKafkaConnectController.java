package com.kenai.br.kafka.admin.service.controller.core.configuration;

import java.util.List;

import com.kenai.br.kafka.admin.service.controller.DefaultController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kenai.br.kafka.admin.service.configuration.Paths;
import com.kenai.br.kafka.admin.service.dto.core.ConfigurationKafkaConnectResponse;
import com.kenai.br.kafka.admin.service.service.core.ConfigurationKafkaConnectService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(Paths.CORE_KAFKA_CONNECT_BASE)
public class ConfigurationKafkaConnectController extends DefaultController {


    private final ConfigurationKafkaConnectService service;

    @Autowired
    public ConfigurationKafkaConnectController(ConfigurationKafkaConnectService service) {
        this.service = service;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<ConfigurationKafkaConnectResponse>> findAll() {
        return new ResponseEntity<>(this.service.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<ConfigurationKafkaConnectResponse> find(@PathVariable Long id) {
        return  new ResponseEntity<>(this.service.findById(id), HttpStatus.OK);
    }
}
