package com.kenai.br.kafka.admin.service.controller.consumer;

import com.kenai.br.kafka.admin.service.configuration.Paths;
import com.kenai.br.kafka.admin.service.controller.DefaultController;
import com.kenai.br.kafka.admin.service.dto.consumer.ConsumerRequest;
import com.kenai.br.kafka.admin.service.dto.consumer.RecordResponse;
import com.kenai.br.kafka.admin.service.service.KafkaConsumerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
@RequestMapping(Paths.CORE_CONSUMER)
public class ConsumerController extends DefaultController {

    private final KafkaConsumerService service;

    @Autowired
    public ConsumerController(KafkaConsumerService service) {
        this.service = service;
    }

    @PostMapping(path = "/{name}/create", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String> createConsumer(@PathVariable("name") String consumerName, @RequestBody ConsumerRequest request) {
        return new ResponseEntity<>(this.service.createConsumer(super.getHeader(), consumerName, request), HttpStatus.OK);
    }

    @GetMapping(path = "/{name}/{id}/records")
    @ResponseBody
    public ResponseEntity<List<RecordResponse>> consumeRecords(@PathVariable("name") String consumerName,@PathVariable("id") String consumerInstanceId) {
        return new ResponseEntity<>(this.service.getRecords(super.getHeader(), consumerName, consumerInstanceId), HttpStatus.OK);
    }

    @GetMapping(path = "/pure/{consumerGroupName}/{topicName}/records")
    @ResponseBody
    public ResponseEntity<List<RecordResponse>> consumeRecordsPureKafka(@PathVariable("consumerGroupName") String consumerGroupName,@PathVariable("topicName") String topicName) {
        return new ResponseEntity<>(this.service.getRecordsPureKafka(super.getHeader(), consumerGroupName, topicName), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{name}/{id}")
    public ResponseEntity<Void>deleteConsumer(@PathVariable("name") String consumerName,@PathVariable("id") String consumerInstanceId) {
        this.service.deleteSubscriptionToTopic(super.getHeader(), consumerName, consumerInstanceId);
        return ResponseEntity.noContent().build();
    }
}
