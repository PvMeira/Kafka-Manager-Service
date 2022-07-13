package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaConsumerGroupLagSummary {
    private String consumer_group_id;
    private Long max_lag;
    private Long total_lag;
    private String max_lag_consumer_id;
    private String max_lag_client_id;
    private String max_lag_instance_id;
    private String max_lag_topic_name;
    private Long max_lag_partition_id;
}
