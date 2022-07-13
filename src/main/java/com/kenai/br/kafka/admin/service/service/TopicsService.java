package com.kenai.br.kafka.admin.service.service;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kenai.br.kafka.admin.service.dto.HeaderRequest;
import com.kenai.br.kafka.admin.service.dto.consumergroups.ConsumerGroupsResponse;
import com.kenai.br.kafka.admin.service.dto.external.topics.KafkaTopic;
import com.kenai.br.kafka.admin.service.dto.external.topics.KafkaTopicKind;
import com.kenai.br.kafka.admin.service.dto.external.topics.TopicProduceResponse;
import com.kenai.br.kafka.admin.service.dto.external.topics.configs.Configuration;
import com.kenai.br.kafka.admin.service.dto.topic.TopicProduceRequestV2;
import com.kenai.br.kafka.admin.service.dto.topic.TopicRequest;
import com.kenai.br.kafka.admin.service.dto.external.topics.Partitions;
import com.kenai.br.kafka.admin.service.exception.ApiException;
import com.kenai.br.kafka.admin.service.exception.KafkaPureException;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import com.kenai.br.kafka.admin.service.service.core.ConfigurationClusterService;
import com.kenai.br.kafka.admin.service.service.core.kafka.PureTopicsService;
import lombok.extern.java.Log;
import lombok.var;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.kenai.br.kafka.admin.service.configuration.ExternalPaths.*;

@Log
@Service
public class TopicsService extends KafkaServiceV2 {

    private final ConfigurationClusterService service;
    private final PureTopicsService pureTopicsService;

    @Autowired
    public TopicsService(ConfigurationClusterService service, PureTopicsService pureTopicsService) {
        this.service = service;
        this.pureTopicsService = pureTopicsService;
    }

    public List<KafkaTopic> getAllTopics(HeaderRequest request) {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));

        if (configurationCluster.isUseConfluentApi()) {
            var response = (String) super.get(  request.getConfigurationName()
                    , String.format(TOPIC_BASE, this.getSchemaVersion(request.getConfigurationName()), request.getClusterID())
                    , false);
            return super.gson().fromJson(response,KafkaTopicKind.class).getData();
        }
        return this.pureTopicsService.getAllTopics(configurationCluster);

    }

    public KafkaTopic find(HeaderRequest request, String name) {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));

        if (configurationCluster.isUseConfluentApi()) {
            var response = (String) super.get(  request.getConfigurationName()
                                              , String.format(TOPIC_FIND, this.getSchemaVersion(request.getConfigurationName()), request.getClusterID(), name)
                                              , false);
            return super.gson().fromJson(response, KafkaTopic.class);

        }

        return this.pureTopicsService.get(name, configurationCluster);

    }

    public void save(HeaderRequest headerRequest, TopicRequest request) {
        ConfigurationCluster configurationCluster = this.service.findByName(headerRequest.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));
        if (configurationCluster.isUseConfluentApi()) {
            super.post(  headerRequest.getConfigurationName()
                       , String.format(TOPIC_BASE, this.getSchemaVersion(  headerRequest.getConfigurationName()), headerRequest.getClusterID())
                       , request.toEntity()
                       , false);
        }
        this.pureTopicsService.create(configurationCluster, request);
    }

    public void delete(HeaderRequest request, String name) {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));
        if (configurationCluster.isUseConfluentApi()) {
            super.delete(  request.getConfigurationName()
                         , String.format(TOPIC_DELETE, this.getSchemaVersion(request.getConfigurationName()), request.getClusterID(), name)
                         , false);
        }
        this.pureTopicsService.delete(name, configurationCluster);

    }


    public List<Partitions> getAllPartitions(HeaderRequest request, String name) {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));

        if (configurationCluster.isUseConfluentApi()) {
            var response = (String) super.get(  request.getConfigurationName()
                                              , String.format(TOPIC_PARTITIONS, name)
                                              ,  false);
            return super.gson().fromJson(response, new TypeToken<ArrayList<Partitions>>() {}.getType());
        }

        return new ArrayList<>();
    }

    public List<ConsumerGroupsResponse> getConsumerGroups(HeaderRequest request, String topicName)  {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));
        return this.pureTopicsService.getConsumerGroupsFromTopic(configurationCluster, topicName);

    }


        public List<Configuration> getAllConfig(HeaderRequest request, String name) {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));

        if (configurationCluster.isUseConfluentApi()) {


            var response = (String) super.get(request.getConfigurationName()
                    , String.format(TOPIC_CONFIGS
                            , this.getSchemaVersion(request.getConfigurationName())
                            , request.getClusterID(), name)
                    , false);
            return super.gson().fromJson(this.gson().fromJson(response, JsonObject.class).get("data").toString(), new TypeToken<ArrayList<Configuration>>() {
            }.getType());
        }
        return this.pureTopicsService.getTopicConfig(configurationCluster, name);
        }



    public TopicProduceResponse produce(HeaderRequest request, String topicName, TopicProduceRequestV2 requestV2) throws ExecutionException, InterruptedException {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));

        return this.pureTopicsService.produceToTopic(topicName, configurationCluster, requestV2.getKeyValue(), requestV2.getValue(), requestV2.getKeySerializer(), requestV2.getValueSerializer(), null);

    }
    public TopicProduceResponse produceToSinglePartition(HeaderRequest request, String topicName, TopicProduceRequestV2 requestV2, String partition) throws ExecutionException, InterruptedException {
        ConfigurationCluster configurationCluster = this.service.findByName(request.getConfigurationName()).orElseThrow(() -> new ApiException("No conf was found"));

        return this.pureTopicsService.produceToTopic(topicName, configurationCluster, requestV2.getKeyValue(), requestV2.getValue(), requestV2.getKeySerializer(), requestV2.getValueSerializer(), partition);
    }
}
