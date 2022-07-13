package com.kenai.br.kafka.admin.service.repository;

import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationClusterRepository extends CrudRepository<ConfigurationCluster, String> {
}
