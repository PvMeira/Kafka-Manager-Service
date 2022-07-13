package com.kenai.br.kafka.admin.service.service.core.kafka;

import com.kenai.br.kafka.admin.service.dto.consumer.RecordResponse;
import com.kenai.br.kafka.admin.service.dto.consumergroups.ConsumerGroupsGenerlResponse;
import com.kenai.br.kafka.admin.service.dto.consumergroups.ConsumerGroupsResponse;
import com.kenai.br.kafka.admin.service.exception.KafkaPureException;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import com.kenai.br.kafka.admin.service.service.KafkaServiceV2;
import com.kenai.br.kafka.admin.service.util.Base64Deserializer;
import lombok.extern.java.Log;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Log
@Service
public class PureConsumersService extends KafkaServiceV2 {

    public ConsumerGroupsGenerlResponse getConsumerGroups(ConfigurationCluster configurationCluster) throws KafkaPureException {
        List<ConsumerGroupsResponse> data = new ArrayList<>();
        try{
            final AdminClient admin = super.getAdminClient(configurationCluster.getBootstrapServer());
            admin.listConsumerGroups().all().get().forEach(cg -> {
                Long partitions = 0l;
                try {
                    partitions = admin.listConsumerGroupOffsets(cg.groupId()).partitionsToOffsetAndMetadata().get().values().stream().count();
                } catch (Exception e) {
                    log.warning("Cannot fetch the MetaDataInfo for this Consumer Group.");
                }
                data.add(ConsumerGroupsResponse
                        .builder()
                        .withState(cg.state().get().name())
                        .withConsumerGroupId(cg.groupId())
                        .withMaxLag(null)
                        .withPartitions(partitions)
                        .withMembers(null)
                        .build());
            });

        } catch (Exception e) {
            e.printStackTrace();
            throw new KafkaPureException("Error while trying to retrieve the Topics information.");
        }
        return ConsumerGroupsGenerlResponse
               .builder()
               .withActive(data.stream().filter(a-> a.getState().equalsIgnoreCase("STABLE")).count())
               .withDead(data.stream().filter(a-> a.getState().equalsIgnoreCase("DEAD")).count())
               .withRebalancing(data.stream().filter(a-> a.getState().equalsIgnoreCase("PREPARING_REBALANCE")).count())
               .withEmpty(data.stream().filter(a-> a.getState().equalsIgnoreCase("EMPTY")).count())
               .withData(data)
               .build();
    }



    public List<RecordResponse> consumeRecordsTemp(String topicName, ConfigurationCluster configurationCluster) {
        final Properties props = new Properties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, configurationCluster.getBootstrapServer());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Base64Deserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Base64Deserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        final Consumer<String, String> consumer = new KafkaConsumer<>(props);
        List<RecordResponse> resultSet = new ArrayList<>();
        consumer.subscribe(Collections.singletonList(topicName));

        final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(100L));

        if (consumerRecords.isEmpty())
            return resultSet;

        consumerRecords.forEach(record -> {
            resultSet.add(RecordResponse.builder()
                                        .withTopic(record.topic())
                                        .withValue(record.value())
                                        .withKey(record.key())
                                        .withOffset(record.offset())
                                        .withPartition(record.offset())
                                        .build());
            System.out.println("Record" + record.key());
            System.out.println("Value : " + record.value());
        });

        consumer.commitAsync();
        consumer.unsubscribe();
        consumer.close();

        return  resultSet;
    }

    public List<RecordResponse> consumeRecords(String consumerGroupId, String topicName, ConfigurationCluster configurationCluster) throws KafkaPureException {
        final Properties a = new Properties();
        a.put(ConsumerConfig.GROUP_ID_CONFIG,consumerGroupId);
        a.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, UUID.randomUUID().toString());
        a.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, configurationCluster.getBootstrapServer());
        a.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Base64Deserializer.class.getName());
        a.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Base64Deserializer.class.getName());
        a.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        a.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        final Consumer<String, String> consumer = new KafkaConsumer<>(a);
        List<RecordResponse> resultSet = new ArrayList<>();
        consumer.subscribe(Collections.singletonList(topicName));



        final ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));


        if (consumerRecords.isEmpty())
            return resultSet;

        consumerRecords.forEach(record -> {
            resultSet.add(RecordResponse.builder()
                                        .withTopic(record.topic())
                                        .withValue(record.value())
                                        .withKey(record.key())
                                        .withOffset(record.offset())
                                        .withPartition(record.offset())
                                        .build());
            System.out.println("Record" + record.key());
            System.out.println("Value : " + record.value());
        });

        consumer.commitAsync();
        consumer.close();

        return  resultSet;
    }

}
