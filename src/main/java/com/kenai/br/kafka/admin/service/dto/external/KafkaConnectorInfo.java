package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaConnectorInfo {
    private String name;
    private Map<String, String> config;
    private List<KafkaTask>tasks;
    private String type;
}
