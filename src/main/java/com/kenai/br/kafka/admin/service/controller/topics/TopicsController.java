package com.kenai.br.kafka.admin.service.controller.topics;

import com.kenai.br.kafka.admin.service.configuration.Paths;
import com.kenai.br.kafka.admin.service.controller.DefaultController;
import com.kenai.br.kafka.admin.service.dto.consumergroups.ConsumerGroupsResponse;
import com.kenai.br.kafka.admin.service.dto.topic.TopicProduceRequestV2;
import com.kenai.br.kafka.admin.service.dto.topic.TopicProduceResponse;
import com.kenai.br.kafka.admin.service.dto.topic.TopicRequest;
import com.kenai.br.kafka.admin.service.dto.topic.TopicResponse;
import com.kenai.br.kafka.admin.service.dto.topic.configs.ConfigurationResponse;
import com.kenai.br.kafka.admin.service.dto.topic.partitions.PartitionsResponse;
import com.kenai.br.kafka.admin.service.service.TopicsService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Log
@RestController
@RequestMapping(Paths.TOPICS_URL_BASE)
public class TopicsController extends DefaultController {

    private final TopicsService service;

    @Autowired
    public TopicsController(TopicsService service) {
        this.service = service;
    }


    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<TopicResponse>> findAll() {
        return new ResponseEntity<>(this.service.getAllTopics(super.getHeader()).stream().map(TopicResponse::parse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<TopicResponse> find(@PathVariable("name")String name) {
        return new ResponseEntity<>(TopicResponse.parse(this.service.find(super.getHeader(), name)), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void save(@RequestBody TopicRequest request) {
        this.service.save(super.getHeader(), request);
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable("name")String name) {
        this.service.delete(super.getHeader(), name);
    }

    @GetMapping(value = "/{name}/partitions",  produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<PartitionsResponse>>getPartitions(@PathVariable("name")String name) {
        return new ResponseEntity<>(this.service.getAllPartitions(super.getHeader(), name).stream().map(PartitionsResponse::parse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{name}/configs",  produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<ConfigurationResponse>>getConfigs(@PathVariable("name")String name) {
        this.service.getAllConfig(super.getHeader(), name);
        return new ResponseEntity<>(this.service.getAllConfig(super.getHeader(), name).stream().map(ConfigurationResponse::parse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{name}/consumer-group",  produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<ConsumerGroupsResponse>>getConsumerGorup(@PathVariable("name")String name) {
        return new ResponseEntity<>(this.service.getConsumerGroups(super.getHeader(), name), HttpStatus.OK);
    }

    @PostMapping(  value = "/{name}/produce", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.ALL_VALUE})
    @ResponseBody
    public ResponseEntity<TopicProduceResponse> produceMessage(@PathVariable("name")String name, @RequestBody TopicProduceRequestV2 body) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(TopicProduceResponse.parse(  this.service.produce(super.getHeader(), name, body))
                                                               , HttpStatus.OK);
    }


    @PostMapping(  value = "/{name}/produce/partitions/{number}", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.ALL_VALUE})
    @ResponseBody
    public ResponseEntity<TopicProduceResponse> produceMessageToSpecificPartition(@PathVariable("name")String name, @PathVariable("number")String number, @RequestBody TopicProduceRequestV2 body) throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(TopicProduceResponse.parse(  this.service.produceToSinglePartition( super.getHeader(), name, body, number))
                                                               , HttpStatus.OK);
    }
}
