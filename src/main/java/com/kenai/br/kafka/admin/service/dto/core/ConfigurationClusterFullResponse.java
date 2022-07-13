package com.kenai.br.kafka.admin.service.dto.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigurationClusterFullResponse {

    private String clusterName;

    private String bootstrapServer;

    private String zookeeper;

    private String additionalProperties;

    private String color;

    private String schemaVersion;

    private String schemaRegistryUrl;

    private String schemaRegistrySecurityType;

    private String schemaRegistrySecurityUser;

    private String schemaRegistrySecurityPass;

    private String schemaRegistrySecurityToken;

    private String schemaRegistryAdditionalProperties;

    private List<ConfigurationKafkaConnectResponse> kafkaConnects;

    private String clusterId;

    private boolean useConfluentApi;

    private String urlConfluentRestServer;

    public static ConfigurationClusterFullResponse parse(ConfigurationCluster c) {
        return ConfigurationClusterFullResponse
                .builder()
                .withClusterName(c.getClusterName())
                .withBootstrapServer(c.getBootstrapServer())
                .withZookeeper(c.getZookeeper())
                .withAdditionalProperties(c.getAdditionalProperties())
                .withColor(c.getColor())
                .withSchemaVersion(c.getSchemaVersion())
                .withSchemaRegistryUrl(c.getSchemaRegistryUrl())
                .withSchemaRegistrySecurityType(c.getSchemaRegistrySecurityType())
                .withSchemaRegistrySecurityUser(c.getSchemaRegistrySecurityUser())
                .withSchemaRegistrySecurityPass(c.getSchemaRegistrySecurityPass())
                .withSchemaRegistrySecurityToken(c.getSchemaRegistrySecurityToken())
                .withSchemaRegistryAdditionalProperties(c.getSchemaRegistryAdditionalProperties())
                .withUseConfluentApi(c.isUseConfluentApi())
                .withUrlConfluentRestServer(c.getUrlConfluentRestServer())
                .withKafkaConnects(c.getKafkaConnects().stream().map(ConfigurationKafkaConnectResponse::parse).collect(Collectors.toList()))
                .build();
    }


}
