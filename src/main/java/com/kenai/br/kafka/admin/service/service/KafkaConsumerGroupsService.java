package com.kenai.br.kafka.admin.service.service;

import com.kenai.br.kafka.admin.service.dto.HeaderRequest;
import com.kenai.br.kafka.admin.service.dto.consumer.ConsumerResponse;
import com.kenai.br.kafka.admin.service.dto.consumergroups.ConsumerGroupsGenerlResponse;
import com.kenai.br.kafka.admin.service.dto.consumergroups.ConsumerGroupsResponse;
import com.kenai.br.kafka.admin.service.dto.external.KafkaConsumerGroupKind;
import com.kenai.br.kafka.admin.service.dto.external.KafkaConsumerGroupLagSummary;
import com.kenai.br.kafka.admin.service.dto.external.KafkaConsumerKind;
import com.kenai.br.kafka.admin.service.exception.ApiException;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import com.kenai.br.kafka.admin.service.service.core.ConfigurationClusterService;
import com.kenai.br.kafka.admin.service.service.core.kafka.PureConsumersService;
import lombok.extern.java.Log;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.kenai.br.kafka.admin.service.configuration.ExternalPaths.CONSUMER_GROUPS_BASE;
import static com.kenai.br.kafka.admin.service.configuration.ExternalPaths.CONSUMER_GROUPS_FIND_BASE;

@Log
@Service
public class KafkaConsumerGroupsService extends KafkaServiceV2 {

    private final ConfigurationClusterService service;
    private final PureConsumersService pureConsumersService;

    @Autowired
    public KafkaConsumerGroupsService(ConfigurationClusterService service, PureConsumersService pureConsumersService) {
        this.service = service;
        this.pureConsumersService = pureConsumersService;
    }

    public List<ConsumerResponse> getByGroupName(HeaderRequest request, String groupId) {
        var response = (String) super.get(  request.getConfigurationName()
                                          ,  String.format(  CONSUMER_GROUPS_FIND_BASE
                                                           , this.getSchemaVersion(request.getConfigurationName())
                                                           , request.getClusterID()
                                                           , groupId)
                                          , false);
        var consumerKind  = super.gson().fromJson(response, KafkaConsumerKind.class);
        return consumerKind.getData().stream().map(ConsumerResponse::parse).collect(Collectors.toList());
    }

    public ConsumerGroupsGenerlResponse getAll(HeaderRequest request) {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));


        if (configurationCluster.isUseConfluentApi()) {
            var response = (String) super.get(  request.getConfigurationName(), String.format(  CONSUMER_GROUPS_BASE
                                                                                              , this.getSchemaVersion(request.getConfigurationName())
                                                                                              , request.getClusterID())
                                              , false);
            var kafkaConsumerGorup = super.gson().fromJson(response, KafkaConsumerGroupKind.class);
            List<ConsumerGroupsResponse> data =  kafkaConsumerGorup
                                                 .getData()
                                                 .stream()
                                                 .map(a-> {
                                                     ConsumerGroupsResponse groupsResponse = ConsumerGroupsResponse.parse(a);
                                                     try {
                                                         KafkaConsumerGroupLagSummary aditionalLagSummary = this.getAditionalLagSummary(a.getLag_summary().getRelated());
                                                         groupsResponse.setMaxLag(aditionalLagSummary.getMax_lag());
                                                         groupsResponse.setTotalLag(aditionalLagSummary.getTotal_lag());
                                                     } catch (Exception e) {
                                                         e.printStackTrace();
                                                     }
                                                     return groupsResponse;
                                                 })
                                                 .collect(Collectors.toList());
            return ConsumerGroupsGenerlResponse
                   .builder()
                   .withActive(data.stream().filter(a-> a.getState().equalsIgnoreCase("STABLE")).count())
                   .withDead(data.stream().filter(a-> a.getState().equalsIgnoreCase("DEAD")).count())
                   .withRebalancing(data.stream().filter(a-> a.getState().equalsIgnoreCase("PREPARING_REBALANCE")).count())
                   .withEmpty(data.stream().filter(a-> a.getState().equalsIgnoreCase("EMPTY")).count())
                   .withData(data)
                   .build();
        }

        return this.pureConsumersService.getConsumerGroups(configurationCluster);

    }

    public KafkaConsumerGroupLagSummary getAditionalLagSummary(String url) {
        var response =   (String) super.get(url);
        return super.gson().fromJson(response, KafkaConsumerGroupLagSummary.class);
    }
}
