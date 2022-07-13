package com.kenai.br.kafka.admin.service.controller.connect;

import com.kenai.br.kafka.admin.service.configuration.Paths;
import com.kenai.br.kafka.admin.service.controller.DefaultController;
import com.kenai.br.kafka.admin.service.dto.connector.*;
import com.kenai.br.kafka.admin.service.dto.external.KafkaConnectorPlugin;
import com.kenai.br.kafka.admin.service.enumerate.RuntineStatusType;
import com.kenai.br.kafka.admin.service.service.KafkaConnectorService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping(Paths.CONNECTORS_URL_BASE)
public class ConnectorsController extends DefaultController {

    private final KafkaConnectorService connectorsService;

    @Autowired
    public ConnectorsController(KafkaConnectorService connectorsService) {
        this.connectorsService = connectorsService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<ConnectorsResponse> findAll(@RequestParam(required = false) String name) {
        return new ResponseEntity<>(this.connectorsService.getAllConnectors(super.getHeader()), HttpStatus.OK);
    }

    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<ConnectorResponse> find(@PathVariable("name") String name) {
        return new ResponseEntity<>(this.connectorsService.getConnector(name, super.getHeader()), HttpStatus.OK);
    }

    @GetMapping(value = "/{name}/status", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<ConnectorStatusResponse> checkStatus(@PathVariable("name") String name) {
        return new ResponseEntity<>(this.connectorsService.getConnectorStatus(name, super.getHeader()), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void save(@RequestBody ConnectorRequest request) {
        this.connectorsService.saveConnector(request, super.getHeader());
    }

    @PutMapping(value = "/{name}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@PathVariable("name") String name, @RequestBody ConnectorRequest request) {
        this.connectorsService.updateConfigConnector(name, request, super.getHeader());
    }

    @DeleteMapping(value = "/{name}")
    public void delete(@PathVariable("name") String name) {
        this.connectorsService.deleteConnector(name, super.getHeader());
    }

    @GetMapping(value = "/plugins", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<ConnectorPluginResponse>> getAllPlugins() {
        return new ResponseEntity<>(this.connectorsService.getPlugins(super.getHeader()), HttpStatus.OK);
    }

    @GetMapping(value = "/{name}/topics", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<String>> getTopics(@PathVariable("name") String name) {
        return new ResponseEntity<>(this.connectorsService.getTopicsFromConnector(super.getHeader(), name), HttpStatus.OK);
    }

    @PutMapping(value = "/{name}/pause")
    public void pause(@PathVariable("name") String name) {
        this.connectorsService.changeRuntimeStatus(RuntineStatusType.PAUSE, name, super.getHeader());
    }

    @PutMapping(value = "/{name}/resume")
    public void resume(@PathVariable("name") String name) {
        this.connectorsService.changeRuntimeStatus(RuntineStatusType.RESUME, name, super.getHeader());
    }

    @PostMapping(value = "/{name}/restart")
    public void restart(@PathVariable("name") String name) {
        this.connectorsService.changeRuntimeStatus(RuntineStatusType.RESTART, name, super.getHeader());
    }
}

