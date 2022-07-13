package com.kenai.br.kafka.admin.service.dto.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigurationClusterRequest {

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

    private List<ConfigurationKafkaConnectRequest> kafkaConnects;

    private boolean useConfluentApi;

    private String urlConfluentRestServer;


    public ConfigurationCluster toEntity() {
        return ConfigurationCluster
        .builder()
                .withClusterName(this.clusterName)
                .withBootstrapServer(this.bootstrapServer)
                .withZookeeper(this.zookeeper)
                .withAdditionalProperties(this.additionalProperties)
                .withColor(this.color)
                .withSchemaVersion(this.schemaVersion)
                .withSchemaRegistryUrl(this.schemaRegistryUrl)
                .withSchemaRegistrySecurityType(this.schemaRegistrySecurityType)
                .withSchemaRegistrySecurityUser(this.schemaRegistrySecurityUser)
                .withSchemaRegistrySecurityPass(this.schemaRegistrySecurityPass)
                .withSchemaRegistrySecurityToken(this.schemaRegistrySecurityToken)
                .withSchemaRegistryAdditionalProperties(this.schemaRegistryAdditionalProperties)
                .withUseConfluentApi(this.useConfluentApi)
                .withUrlConfluentRestServer(this.urlConfluentRestServer)
                .withKafkaConnects(null != this.kafkaConnects ? this.kafkaConnects.stream().map(a -> ConfigurationKafkaConnectRequest.toEntity(a, this.clusterName)).collect(Collectors.toList()) : new ArrayList<>())

        .build();
    }
}
