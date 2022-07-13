package com.kenai.br.kafka.admin.service.dto.connector;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.dto.external.KafkaConnectorPlugin;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectorPluginResponse {
    private String className;
    private String type;
    private String version;


    public static ConnectorPluginResponse parse(KafkaConnectorPlugin p) {
        return ConnectorPluginResponse.builder()
                .withClassName(p.getClassName())
                .withType(p.getType())
                .withVersion(p.getVersion())
                .build();
    }
}
