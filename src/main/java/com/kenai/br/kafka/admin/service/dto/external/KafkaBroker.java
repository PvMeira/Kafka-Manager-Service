package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")

public class KafkaBroker {
    private String kind;
    private Long broker_id;
    private String host;
    private Long port;
    private String rack;
    private Map<String, String> configs;
}
