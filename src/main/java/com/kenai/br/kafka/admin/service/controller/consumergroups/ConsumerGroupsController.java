package com.kenai.br.kafka.admin.service.controller.consumergroups;

import com.kenai.br.kafka.admin.service.configuration.Paths;
import com.kenai.br.kafka.admin.service.controller.DefaultController;
import com.kenai.br.kafka.admin.service.dto.consumer.ConsumerResponse;
import com.kenai.br.kafka.admin.service.dto.consumergroups.ConsumerGroupsGenerlResponse;
import com.kenai.br.kafka.admin.service.service.KafkaConsumerGroupsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping(Paths.CORE_CONSUMER_GROUPS)
public class ConsumerGroupsController extends DefaultController {

    private final KafkaConsumerGroupsService service;

    @Autowired
    public ConsumerGroupsController(KafkaConsumerGroupsService service) {
        this.service = service;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<ConsumerGroupsGenerlResponse> findAll() {
        return new ResponseEntity<>(this.service.getAll(super.getHeader()), HttpStatus.OK);
    }

    @GetMapping(value = "/{group_name}",produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<ConsumerResponse>> find(@PathVariable("group_name")String id) {
        return new ResponseEntity<>(this.service.getByGroupName(super.getHeader(), id), HttpStatus.OK);
    }
}
