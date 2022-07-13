package com.kenai.br.kafka.admin.service.service;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kenai.br.kafka.admin.service.configuration.ExternalPaths;
import com.kenai.br.kafka.admin.service.dto.HeaderRequest;
import com.kenai.br.kafka.admin.service.dto.consumer.ConsumerRequest;
import com.kenai.br.kafka.admin.service.dto.consumer.RecordResponse;
import com.kenai.br.kafka.admin.service.exception.ApiException;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import com.kenai.br.kafka.admin.service.service.core.ConfigurationClusterService;
import com.kenai.br.kafka.admin.service.service.core.kafka.PureConsumersService;
import com.kenai.br.kafka.admin.service.util.MediaTypeUtility;
import lombok.extern.java.Log;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log
@Service
public class KafkaConsumerService extends KafkaServiceV2 {

    private final ConfigurationClusterService service;
    private final PureConsumersService pureService;

    @Autowired
    public KafkaConsumerService(ConfigurationClusterService service, PureConsumersService a) {
        this.service = service;
        this.pureService = a;
    }

    public String createConsumerInstance(HeaderRequest request, String consumerGoupName, HashMap<String,String>props) {
        var response = (String) super.post(  request.getConfigurationName()
                                           , String.format(ExternalPaths.CONSUMER, consumerGoupName)
                                           , props
                                           , MediaTypeUtility.APPLICATION_VND_KAFKA_JSON_v2JSON
                                           , false);
        if (response.contains("error_code"))
            throw new ApiException("Error on the cration of the consumer instance : " + response);

        return super.gson().fromJson(response, JsonObject.class).get("instance_id").getAsString();
    }

    public void subscribeToTopic(HeaderRequest request, String consumerGoupName, String instanceId, Object body) {
        var response = (String) super.post(  request.getConfigurationName()
                                           , String.format(ExternalPaths.CONSUMER_SUBSCRIPTION, consumerGoupName, instanceId)
                                           , body
                                           , MediaTypeUtility.APPLICATION_VND_KAFKA_JSON_v2JSON
                                           , false);
        if (response != null && response.contains("error_code"))
            throw new ApiException("Error on subscription to the topic : " + response);

    }

    public List<RecordResponse> getRecords(HeaderRequest request, String consumerGoupName, String instanceId) {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));
        if (configurationCluster.isUseConfluentApi()) {
            var response = (String) super.get(  request.getConfigurationName()
                                              , String.format(ExternalPaths.CONSUMER_RECORDS, consumerGoupName, instanceId)
                                              , MediaTypeUtility.APPLICATION_VND_KAFKA_JSON_v2JSON
                                              , false);
            if (response.contains("error_code"))
                throw new ApiException("Error on getting the records from the topic : " + response);

            return super.gson().fromJson(response,new TypeToken<ArrayList<RecordResponse>>(){}.getType());
        } else {
            // TODO create a proper ConsumeRecord that reuse the same group
            return this.pureService.consumeRecordsTemp(consumerGoupName, configurationCluster);
        }

    }

    public List<RecordResponse> getRecordsPureKafka(HeaderRequest request, String consumerGroupName, String topicName) {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));
        if (configurationCluster.isUseConfluentApi())
            throw new ApiException("This Configuration is set to use Confluent API");

        return this.pureService.consumeRecords(consumerGroupName, topicName, configurationCluster);

    }

    public String createConsumer(HeaderRequest headerRequest, String consumerGoupName, ConsumerRequest request) {
        ConfigurationCluster configurationCluster = this.service.findByName(headerRequest.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));
        if (configurationCluster.isUseConfluentApi()) {
            String id = this.createConsumerInstance(headerRequest, consumerGoupName, request.getProps());
            request.setProps(null);
            this.subscribeToTopic(headerRequest, consumerGoupName,id, request);
            return id;
        } else {
            return consumerGoupName;
        }

    }

    public void deleteSubscriptionToTopic(HeaderRequest request, String consumerGoupName, String instanceId) {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));

        if (configurationCluster.isUseConfluentApi()) {


            var response = (String) super.delete(  request.getConfigurationName()
                                                 , String.format(ExternalPaths.CONSUMER_SUBSCRIPTION, consumerGoupName, instanceId)
                                                 , MediaTypeUtility.APPLICATION_VND_KAFKA_JSON_v2JSON
                                                 , false);
            if (response != null && response.contains("error_code"))
                throw new ApiException("Error on removing the subscription to the topic : " + response);
        }
    }
}
