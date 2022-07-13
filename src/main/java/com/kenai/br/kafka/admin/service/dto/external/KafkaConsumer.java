package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaConsumer {
    private String consumer_group_id;
    private String consumer_id;
    private String instance_id;
    private String client_id;

}
