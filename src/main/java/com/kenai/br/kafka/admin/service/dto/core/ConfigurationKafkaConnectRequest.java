package com.kenai.br.kafka.admin.service.dto.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.model.ConfigurationKafkaConnect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigurationKafkaConnectRequest {

    private Long id;

    private String name;

    private String url;

    private String httpHeaders;

    private String securityType;

    private String securityUser;

    private String securityPass;

        private String securityToken;

    private String additionalProperties;

    public static ConfigurationKafkaConnect toEntity(ConfigurationKafkaConnectRequest r, String clusterName) {
        return ConfigurationKafkaConnect.builder()
                .withId(r.getId())
                .withName(r.getName())
                .withUrl(r.getUrl())
                .withHttpHeaders(r.getHttpHeaders())
                .withSecurityType(r.getSecurityType())
                .withSecurityUser(r.getSecurityUser())
                .withSecurityPass(r.getSecurityPass())
                .withSecurityToken(r.getSecurityToken())
                .withAdditionalProperties(r.getAdditionalProperties())
                .withIdConfiguration(clusterName)
                .build();
    }
}
