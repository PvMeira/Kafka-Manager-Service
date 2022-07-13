package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaConsumerGroup {

    private String consumer_group_id;
    private boolean is_simple;
    private String partition_assignor;
    private String state;
    private KafkaRelated lag_summary;
    private KafkaRelated consumers;
    private KafkaRelated coordinator;

}
