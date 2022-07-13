package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaConnectorTask {

    private Long id;
    private String state;
    private String worker_id;
}
