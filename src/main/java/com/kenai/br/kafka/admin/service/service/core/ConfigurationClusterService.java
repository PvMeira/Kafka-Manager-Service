package com.kenai.br.kafka.admin.service.service.core;

import com.kenai.br.kafka.admin.service.dto.core.ConfigurationClusterFullResponse;
import com.kenai.br.kafka.admin.service.exception.ApiException;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import com.kenai.br.kafka.admin.service.repository.ConfigurationClusterRepository;
import com.kenai.br.kafka.admin.service.service.KafkaServiceV2;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Log
@Service
public class ConfigurationClusterService extends KafkaServiceV2 {

    private final ConfigurationClusterRepository repository;

    @Autowired
    public ConfigurationClusterService(ConfigurationClusterRepository repository) {
        this.repository = repository;
    }

    public List<ConfigurationCluster> findAll() {
        return StreamSupport
                .stream(this.repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Optional<ConfigurationCluster> findByName(String name) {
        return this.repository.findById(name);
    }

    public ConfigurationClusterFullResponse findByNameWithClusterId(String name) {
        ConfigurationClusterFullResponse response = this.repository.findById(name).map(ConfigurationClusterFullResponse::parse).get();
        try {
            response.setClusterId(this.getClusterIDTemp(response.getUrlConfluentRestServer(), response.getSchemaVersion()));
        } catch (Exception e) {
            log.warning("Was not possible to establish the connection to the kafka server");
        }
        return response;
    }

    public void create(ConfigurationCluster configuration) {
        this.repository.save(configuration);
    }

    public void update(String name, ConfigurationCluster configuration) {
        this.repository.findById(name).ifPresent(a -> {
            this.repository.save(configuration);
        });
    }

    public void remove(String name) {
        this.repository.findById(name).orElseThrow(() -> new ApiException("No configuraiton was found with name " + name));
        this.repository.deleteById(name);
    }
}
