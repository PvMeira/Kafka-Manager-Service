package com.kenai.br.kafka.admin.service.dto.connector;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.dto.external.KafkaConnectorInfo;
import com.kenai.br.kafka.admin.service.dto.external.KafkaTask;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectorResponse {
    private String name;
    private Map<String, String> config;
    private List<KafkaTask> tasks;
    private String type;


    public static  ConnectorResponse buildExternalToResponse(KafkaConnectorInfo a) {
        return ConnectorResponse
                .builder()
                .withName(a.getName())
                .withTasks(a.getTasks())
                .withType(a.getType())
                .withConfig(a.getConfig())
                .build();
    }

    public static List<ConnectorResponse> buildExternalToResponse(List<KafkaConnectorInfo> a) {
        return a.stream().map(ConnectorResponse::buildExternalToResponse).collect(Collectors.toList());
    }

}
