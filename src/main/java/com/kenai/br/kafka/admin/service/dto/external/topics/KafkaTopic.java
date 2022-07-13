package com.kenai.br.kafka.admin.service.dto.external.topics;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaTopic {

    private String kind;
    private String cluster_id;
    private String topic_name;
    private String is_internal;
    private Long replication_factor;
    private Long partitions_count;

}
