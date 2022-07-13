package com.kenai.br.kafka.admin.service.service;

import com.kenai.br.kafka.admin.service.configuration.ExternalPaths;
import com.kenai.br.kafka.admin.service.dto.core.ConfigurationTestConnectionRequest;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class KafkaSchemaRegistryService extends KafkaServiceV2 {

    public void testConnection(ConfigurationTestConnectionRequest request) {
        super.get(request.getServerUrl() + ExternalPaths.SCHEMA_REGISTRY_BASE);
    }
}
