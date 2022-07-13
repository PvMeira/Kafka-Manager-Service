package com.kenai.br.kafka.admin.service.service.core.kafka;


import com.kenai.br.kafka.admin.service.dto.brokers.BrokerResponse;
import com.kenai.br.kafka.admin.service.dto.brokers.BrokersResponse;
import com.kenai.br.kafka.admin.service.dto.external.KafkaBrokerConfig;
import com.kenai.br.kafka.admin.service.dto.external.KafkaSynonyms;
import com.kenai.br.kafka.admin.service.dto.log.LogResponse;
import com.kenai.br.kafka.admin.service.exception.KafkaPureException;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import com.kenai.br.kafka.admin.service.service.KafkaServiceV2;
import com.kenai.br.kafka.admin.service.service.core.ConfigurationClusterService;
import lombok.extern.java.Log;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeConfigsResult;
import org.apache.kafka.clients.admin.LogDirDescription;
import org.apache.kafka.clients.admin.ReplicaInfo;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log
@Service
public class PureBrokerService extends KafkaServiceV2 {

    private final ConfigurationClusterService service;

    @Autowired
    public PureBrokerService(ConfigurationClusterService service) {
        this.service = service;
    }

    public BrokersResponse listAllBrokers(ConfigurationCluster configurationCluster) throws KafkaPureException {
        AdminClient admin = super.getAdminClient(configurationCluster.getBootstrapServer());

        List<BrokerResponse> data = new ArrayList<>(0);

        try {
            for (Node node : admin.describeCluster().nodes().get()) {
                ConfigResource cr = new ConfigResource(ConfigResource.Type.BROKER, node.idString());
                DescribeConfigsResult dcr = admin.describeConfigs(Collections.singleton(cr));
                Map<String, String> confs = new HashMap<>();


                dcr.all().get().forEach((k, c) -> c.entries()
                         .forEach(configEntry -> confs.put(configEntry.name(), configEntry.value() == null ? "" : configEntry.value())));

                data.add(BrokerResponse
                        .builder()
                        .withRack(node.rack())
                        .withBroker_id(Long.valueOf(node.id()))
                        .withHost(node.host())
                        .withPort(Long.valueOf(node.port()))
                        .withKind(null)
                        .withConfigs(confs)
                        .build());

            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new KafkaPureException("Error while trying to retrieve the Brokers information.");

        }

        return BrokersResponse.builder()
                              .withData(data)
                              .withBrokers(data.stream().count())
                              .build();

    }

    public List<LogResponse> getLogs(ConfigurationCluster configurationCluster, String brokerId)  throws KafkaPureException {
        List<Integer> emptyList = new ArrayList<>();
        List<LogResponse> result = new ArrayList<>();
        emptyList.add(Integer.valueOf(brokerId));
        try {
            super.getAdminClient(configurationCluster.getBootstrapServer())
                 .describeLogDirs(emptyList)
                 .allDescriptions()
                 .get()
                 .values().
                 forEach(dirs -> dirs.keySet().forEach(key -> {
                        LogDirDescription logDirDescription = dirs.get(key);
                        logDirDescription.replicaInfos().keySet().forEach(keyTopic -> {
                            ReplicaInfo replicaInfo = logDirDescription.replicaInfos().get(keyTopic);
                            result.add(LogResponse.builder()
                                    .withFolder(key)
                                    .withTopic(keyTopic.topic())
                                    .withPartition(keyTopic.partition())
                                    .withOffsetLag(replicaInfo.offsetLag())
                                    .withSize(replicaInfo.size())
                                    .build());

                });
            }));
        } catch (Exception e) {
            e.printStackTrace();
            throw new KafkaPureException("Error while trying to retrieve the logs information.");
        }
        return result;
    }


        public List<KafkaBrokerConfig> getBrokerProperties(ConfigurationCluster configurationCluster, String brokerId) throws KafkaPureException{
            AdminClient admin = super.getAdminClient(configurationCluster.getBootstrapServer());
        List<KafkaBrokerConfig> result = new ArrayList<>();

        try {
            for (Node node : admin.describeCluster().nodes().get()) {
                if (node.idString().equalsIgnoreCase(brokerId)) {
                    ConfigResource cr = new ConfigResource(ConfigResource.Type.BROKER, node.idString());
                    DescribeConfigsResult dcr = admin.describeConfigs(Collections.singleton(cr));
                    dcr.all().get().forEach((k, c) -> c.entries()
                            .forEach(configEntry -> result.add(KafkaBrokerConfig.builder()
                                            .withCluster_id(node.idString())
                                            .withName(configEntry.name())
                                            .withValue(configEntry.value())
                                            .withIs_read_only(configEntry.isReadOnly())
                                            .withIs_sensitive(configEntry.isSensitive())
                                            .withKind(configEntry.type().toString())
                                            .withIs_default(configEntry.isDefault())
                                            .withSynonyms(configEntry.synonyms().stream().map(a -> KafkaSynonyms.builder()
                                                    .withName(a.name())
                                                    .withValue(a.value())
                                                    .withSource(a.source().name())
                                                    .build()).collect(Collectors.toList()))
                                    .build())));


                }


            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new KafkaPureException("Error while trying to retrieve the Brokers Configuration.");
        }
        return result;
    }
}
