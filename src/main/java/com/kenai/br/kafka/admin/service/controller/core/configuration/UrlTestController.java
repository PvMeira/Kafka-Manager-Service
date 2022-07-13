package com.kenai.br.kafka.admin.service.controller.core.configuration;

import com.kenai.br.kafka.admin.service.configuration.Paths;
import com.kenai.br.kafka.admin.service.controller.DefaultController;
import com.kenai.br.kafka.admin.service.dto.core.ConfigurationTestConnectionRequest;
import com.kenai.br.kafka.admin.service.service.KafkaBrokerService;
import com.kenai.br.kafka.admin.service.service.KafkaConnectorService;
import com.kenai.br.kafka.admin.service.service.KafkaSchemaRegistryService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequestMapping(Paths.CORE_URL_TEST_BASE)
public class UrlTestController extends DefaultController {

    private final KafkaBrokerService brokerService;
    private final KafkaConnectorService connectorService;
    private final KafkaSchemaRegistryService schemaRegistryService;

    @Autowired
    public UrlTestController(KafkaBrokerService brokerService, KafkaConnectorService connectorService, KafkaSchemaRegistryService schemaRegistryService) {
        this.brokerService = brokerService;
        this.connectorService = connectorService;
        this.schemaRegistryService = schemaRegistryService;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity serverTestBroker (@RequestBody ConfigurationTestConnectionRequest request) {
        switch (request.getType()) {
            case BROKER:
                this.brokerService.testConnection(request);
                break;
            case CONNECT:
                this.connectorService.testConnection(request);
                break;
            case SCHEMA_REGISTRY:
                this.schemaRegistryService.testConnection(request);
                break;
        }
        this.brokerService.testConnection(request);
        return ResponseEntity.ok().build();
    }

}
