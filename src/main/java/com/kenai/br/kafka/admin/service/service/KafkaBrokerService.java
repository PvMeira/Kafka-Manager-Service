package com.kenai.br.kafka.admin.service.service;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kenai.br.kafka.admin.service.dto.HeaderRequest;
import com.kenai.br.kafka.admin.service.dto.brokers.BrokerResponse;
import com.kenai.br.kafka.admin.service.dto.brokers.BrokersResponse;
import com.kenai.br.kafka.admin.service.dto.core.ConfigurationTestConnectionRequest;
import com.kenai.br.kafka.admin.service.dto.external.KafkaBrokerConfig;
import com.kenai.br.kafka.admin.service.dto.external.KafkaBrokers;
import com.kenai.br.kafka.admin.service.dto.log.LogResponse;
import com.kenai.br.kafka.admin.service.exception.ApiException;
import com.kenai.br.kafka.admin.service.exception.WebClientException;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import com.kenai.br.kafka.admin.service.service.core.ConfigurationClusterService;
import com.kenai.br.kafka.admin.service.service.core.kafka.PureBrokerService;
import lombok.extern.java.Log;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kenai.br.kafka.admin.service.configuration.ExternalPaths.BROKERS_BASE;
import static com.kenai.br.kafka.admin.service.configuration.ExternalPaths.BROKERS_CONFIG;

@Log
@Service
public class KafkaBrokerService extends KafkaServiceV2 {

    private final ConfigurationClusterService service;
    private final PureBrokerService pureBrokerService;

    @Autowired
    public KafkaBrokerService(ConfigurationClusterService service, PureBrokerService pureBrokerService) {
        this.service = service;
        this.pureBrokerService = pureBrokerService;
    }

    public BrokersResponse getAllBrokers(HeaderRequest header) throws WebClientException  {
        ConfigurationCluster configurationCluster = this.service.findByName(header.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));

        if (configurationCluster.isUseConfluentApi()) {
            var response = (String) super.get(  header.getConfigurationName()
                    , String.format(BROKERS_BASE, this.getSchemaVersion(header.getConfigurationName())
                            , header.getClusterID()));
            var kafkaCluster = super.gson().fromJson(response, KafkaBrokers.class);
            return BrokersResponse
                    .builder()
                    .withBrokers(kafkaCluster.getData().stream().count())
                    .withData(kafkaCluster.getData().stream().map(a -> BrokerResponse.parse(a)).collect(Collectors.toList()))
                    .build();

        }
        return this.pureBrokerService.listAllBrokers(configurationCluster);

    }


    public List<KafkaBrokerConfig> getConf(String brokerId, HeaderRequest header) throws WebClientException  {
        ConfigurationCluster configurationCluster = this.service.findByName(header.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));

        if (configurationCluster.isUseConfluentApi()) {
            var response = (String) super.get(  header.getConfigurationName()
                    , String.format(  BROKERS_CONFIG
                            , this.getSchemaVersion(header.getConfigurationName())
                            , header.getClusterID()
                            , brokerId));
            return super.gson()
                    .fromJson(  this.gson().fromJson(response, JsonObject.class).get("data").toString()
                            , new TypeToken<ArrayList<KafkaBrokerConfig>>(){}.getType());

        }
        return this.pureBrokerService.getBrokerProperties(configurationCluster, brokerId);

    }

    public List<LogResponse> getLogs(String brokerId, HeaderRequest header) {
        ConfigurationCluster configurationCluster = this.service.findByName(header.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));

        return this.pureBrokerService.getLogs(configurationCluster, brokerId);
    }

    public void testConnection(ConfigurationTestConnectionRequest request) throws WebClientException {
        this.get(request.getServerUrl());
    }
}
