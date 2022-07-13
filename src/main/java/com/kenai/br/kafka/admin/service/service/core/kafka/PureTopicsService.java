package com.kenai.br.kafka.admin.service.service.core.kafka;

import com.kenai.br.kafka.admin.service.dto.CustomProps;
import com.kenai.br.kafka.admin.service.dto.consumergroups.ConsumerGroupsResponse;
import com.kenai.br.kafka.admin.service.dto.external.topics.KafkaTopic;
import com.kenai.br.kafka.admin.service.dto.external.topics.Offset;
import com.kenai.br.kafka.admin.service.dto.external.topics.TopicProduceResponse;
import com.kenai.br.kafka.admin.service.dto.external.topics.configs.Configuration;
import com.kenai.br.kafka.admin.service.dto.external.topics.configs.Synonyms;
import com.kenai.br.kafka.admin.service.dto.topic.TopicRequest;
import com.kenai.br.kafka.admin.service.exception.KafkaPureException;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import com.kenai.br.kafka.admin.service.service.KafkaServiceV2;
import com.kenai.br.kafka.admin.service.util.Base64Serializer;
import com.kenai.br.kafka.admin.service.util.GsonSerializer;
import lombok.extern.java.Log;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicCollection;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Log
@Service
public class PureTopicsService extends KafkaServiceV2 {


    public void create(ConfigurationCluster configurationCluster, TopicRequest request) {
        final AdminClient admin = super.getAdminClient(configurationCluster.getBootstrapServer());
        try{
            NewTopic newTopic = new NewTopic(  request.getTopicName()
                                             , request.getPartitionsCount().intValue()
                                             , request.getReplicationFactor().shortValue())

                                .configs(request.getConfigs().stream().collect(Collectors.toMap(CustomProps::getName, item -> item.getValue())));

            admin.createTopics(Collections.singletonList(newTopic));
        } catch (Exception e) {
            e.printStackTrace();
            throw new KafkaPureException("Error while trying to retrieve the Topics information.");
        } finally {
            admin.close();
        }
    }

    public void delete(String topicName, ConfigurationCluster configurationCluster) {
        final AdminClient admin = super.getAdminClient(configurationCluster.getBootstrapServer());
        try{
          admin.deleteTopics(TopicCollection.ofTopicNames(Collections.singletonList(topicName)));
        } catch (Exception e) {
            e.printStackTrace();
            throw new KafkaPureException("Error while trying to retrieve the Topics information.");
        } finally {
            admin.close();
        }
    }

    public List<ConsumerGroupsResponse> getConsumerGroupsFromTopic(ConfigurationCluster configurationCluster, String topicName) throws KafkaPureException {
        List<ConsumerGroupsResponse> result = new ArrayList<>();
        final AdminClient admin = super.getAdminClient(configurationCluster.getBootstrapServer());
        try{
            Collection<ConsumerGroupDescription> consumerGroupDescriptions = admin.describeConsumerGroups(Collections.singleton(topicName))
                                                                                  .all()
                                                                                  .get()
                                                                                  .values();
            consumerGroupDescriptions.forEach(cg -> result.add(ConsumerGroupsResponse
                    .builder()
                            .withState(cg.state().name())
                            .withConsumerId(cg.groupId())
                            .withConsumerGroupId(cg.groupId())
                            .withMaxLag(null)
                            .withMembers(cg.members().stream().count())
                    .build()));


            admin.describeConsumerGroups(Collections.singleton(topicName));
        } catch (Exception e) {
            e.printStackTrace();
            throw new KafkaPureException("Error while trying to retrieve the Topics information.");
        } finally {
            admin.close();
        }
        return result;
    }

    public List<Configuration> getTopicConfig(ConfigurationCluster configurationCluster, String topicName) throws KafkaPureException {
        final AdminClient admin = super.getAdminClient(configurationCluster.getBootstrapServer());
        List<Configuration> result = new ArrayList<>();
        try{
            Collection<ConfigResource> cr = Collections.singleton(new ConfigResource(ConfigResource.Type.TOPIC,topicName));
            DescribeConfigsResult ConfigsResult = admin.describeConfigs(cr);
            Config all_configs = (Config) ConfigsResult.all().get().values().toArray()[0];
            for (ConfigEntry currentConfig : all_configs.entries()) {
                result.add(Configuration.builder()
                                .withName(currentConfig.name())
                                .withValue(currentConfig.value())
                                .withIs_read_only(currentConfig.isReadOnly())
                                .withIs_default(currentConfig.isDefault())
                                .withIs_sensitive(currentConfig.isSensitive())
                                .withSynonyms(currentConfig.synonyms().stream()
                                        .map(a-> Synonyms.builder().withName(a.name())
                                                .withValue(a.value()).withSource(a.source().name()).build()).collect(Collectors.toList()))
                        .build());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new KafkaPureException("Error while trying to retrieve the Topics information.");
        } finally {
            admin.close();
        }
        return result;
    }

    public KafkaTopic get(String name, ConfigurationCluster configurationCluster) {
        KafkaTopic result;
        final AdminClient admin = super.getAdminClient(configurationCluster.getBootstrapServer());
        try{
            TopicDescription topicDescription = admin.describeTopics(Collections.singletonList(name)).allTopicNames().get().values().stream().findFirst().get();
            result = KafkaTopic.builder()
                    .withTopic_name(topicDescription.name())
                    .withCluster_id(admin.describeCluster().clusterId().get())
                    .withIs_internal(topicDescription.isInternal() ? "true" : "false")
                    .withPartitions_count(topicDescription.partitions().stream().count())
                    .withReplication_factor(topicDescription.partitions().stream().map(a-> a.replicas().stream().count()).count())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new KafkaPureException(String.format("Error while trying to retrieve the Topic %s information.", name));
        } finally {
            admin.close();
        }
        return result;

    }

    public List<KafkaTopic> getAllTopics(ConfigurationCluster configurationCluster) throws KafkaPureException {
        List<KafkaTopic> result = new ArrayList<>();
        final AdminClient admin = super.getAdminClient(configurationCluster.getBootstrapServer());

        try {
            admin.listTopics().listings().get().forEach(topicItem -> {
                KafkaTopic topic = KafkaTopic.builder()
                                             .withTopic_name(topicItem.name())
                                             .withIs_internal(topicItem.isInternal() ? "true" : "false")
                                             .build();
                try {
                    TopicDescription topicDescription = admin.describeTopics(new ArrayList<>(Arrays.asList(topicItem.name())))
                                                             .allTopicNames()
                                                             .get()
                                                             .get(topicItem.name());
                    topic.setPartitions_count(topicDescription.partitions().stream().count());
                    topic.setReplication_factor(topicDescription.partitions().stream().map(a-> a.replicas().stream().count()).count());


                } catch (Exception e) {
                   log.warning(String.format("Was not possible to getter more info into the topic %s", topicItem.name()));
                }
                result.add(topic);
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new KafkaPureException("Error while trying to retrieve the Topics information.");

        } finally {
            admin.close();
        }

        return result;
    }


    public TopicProduceResponse produceToTopic(String topicName,
                                               ConfigurationCluster configurationCluster,
                                               Object key,
                                               Object body,
                                               String keySerializer,
                                               String valueSerializer,
                                               String partition) throws KafkaPureException, ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configurationCluster.getBootstrapServer());
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, this.getSerializer(keySerializer));
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, this.getSerializer(valueSerializer));
        props.setProperty(ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        props.setProperty(ProducerConfig.ACKS_CONFIG, "all");



        KafkaProducer<Object, Object> producer = new KafkaProducer<>(props);
        ProducerRecord<Object, Object> record = new ProducerRecord<>(topicName, key, body);
        Future<RecordMetadata> metadataFuture = producer.send(record);
        while (!metadataFuture.isDone()) {
            Thread.sleep(100);
        }

        RecordMetadata recordMetadata = metadataFuture.get();
        return TopicProduceResponse
                .builder()
                .withOffsets(Collections.singletonList(Offset.builder()
                                                             .withOffset(recordMetadata.offset())
                                                             .withPartition(Long.valueOf(recordMetadata.partition()))
                                                             .build()))
                .build();

    }

}
