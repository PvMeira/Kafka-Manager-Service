package com.kenai.br.kafka.admin.service.controller.core.configuration;

import com.kenai.br.kafka.admin.service.configuration.Paths;
import com.kenai.br.kafka.admin.service.dto.core.ConfigurationClusterFullResponse;
import com.kenai.br.kafka.admin.service.dto.core.ConfigurationClusterRequest;
import com.kenai.br.kafka.admin.service.dto.core.ConfigurationClusterResponse;
import com.kenai.br.kafka.admin.service.dto.core.ConfigurationTestConnectionRequest;
import com.kenai.br.kafka.admin.service.service.KafkaBrokerService;
import com.kenai.br.kafka.admin.service.service.KafkaConnectorService;
import com.kenai.br.kafka.admin.service.service.core.ConfigurationClusterService;
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
@RequestMapping(Paths.CORE_CONFIGURATION_BASE)
public class ConfigurationController {

    private final ConfigurationClusterService service;

    @Autowired
    public ConfigurationController(ConfigurationClusterService service) {
        this.service = service;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<ConfigurationClusterResponse>> findAll() {
        return new ResponseEntity<>(this.service.findAll().stream().map(ConfigurationClusterResponse::parse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<ConfigurationClusterFullResponse> find(@PathVariable String name) {
        return  new ResponseEntity<>(this.service.findByNameWithClusterId(name), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity save (@RequestBody ConfigurationClusterRequest request) {
        this.service.create(request.toEntity());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{name}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity update (@PathVariable String name, @RequestBody ConfigurationClusterRequest request) {
        this.service.update(name, request.toEntity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity delete(@PathVariable String name) {
        this.service.remove(name);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
