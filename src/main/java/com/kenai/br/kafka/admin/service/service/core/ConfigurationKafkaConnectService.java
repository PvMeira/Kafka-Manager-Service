package com.kenai.br.kafka.admin.service.service.core;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kenai.br.kafka.admin.service.dto.core.ConfigurationKafkaConnectResponse;
import com.kenai.br.kafka.admin.service.repository.ConfigurationKafkaConnectRepository;

import lombok.extern.java.Log;

@Log
@Service
public class ConfigurationKafkaConnectService {

	private final ConfigurationKafkaConnectRepository repository;

    @Autowired
    public ConfigurationKafkaConnectService(ConfigurationKafkaConnectRepository repository) {
        this.repository = repository;
    }

    public List<ConfigurationKafkaConnectResponse> findAll() {
        return StreamSupport.stream(this.repository
                                        .findAll()
                                        .spliterator(), false)
                            .map(ConfigurationKafkaConnectResponse::parse).collect(Collectors.toList());
    }

    public ConfigurationKafkaConnectResponse findById(Long id) {
        return ConfigurationKafkaConnectResponse.parse(this.repository.findById(id).get());
    }
}
