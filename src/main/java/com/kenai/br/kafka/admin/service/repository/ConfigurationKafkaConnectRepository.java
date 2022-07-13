package com.kenai.br.kafka.admin.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kenai.br.kafka.admin.service.model.ConfigurationKafkaConnect;

@Repository
public interface ConfigurationKafkaConnectRepository extends CrudRepository<ConfigurationKafkaConnect, Long> {
}
