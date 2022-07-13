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
public class ConfigurationKafkaConnectResponse {

    private Long id;

    private String name;

    private String url;

    private String httpHeaders;

    private String securityType;

    private String securityUser;

    private String securityPass;

    private String securityToken;

    private String additionalProperties;

    private String configurationId;


    public static ConfigurationKafkaConnectResponse parse(ConfigurationKafkaConnect c) {
        return ConfigurationKafkaConnectResponse.builder()
                .withId(c.getId())
                .withName(c.getName())
                .withUrl(c.getUrl())
                .withHttpHeaders(c.getHttpHeaders())
                .withSecurityType(c.getSecurityType())
                .withSecurityUser(c.getSecurityUser())
                .withSecurityPass(c.getSecurityPass())
                .withSecurityToken(c.getSecurityToken())
                .withAdditionalProperties(c.getAdditionalProperties())
                .withConfigurationId(c.getIdConfiguration())
                .build();
    }
}
