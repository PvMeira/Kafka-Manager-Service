package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaConnectorStatus {
    private String name;
    private KafkaConnectorStatusConnector connector;
    private List<KafkaConnectorTask> tasks;
    private String type;

}
