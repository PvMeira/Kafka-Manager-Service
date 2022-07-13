package com.kenai.br.kafka.admin.service.dto.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigurationClusterResponse {

    private String clusterName;

    private String bootstrapServer;

    private String color;

    public static ConfigurationClusterResponse parse(ConfigurationCluster c) {
        return ConfigurationClusterResponse.builder()
                .withClusterName(c.getClusterName())
                .withColor(c.getColor())
                .withBootstrapServer(c.getBootstrapServer())
                .build();
    }

}
