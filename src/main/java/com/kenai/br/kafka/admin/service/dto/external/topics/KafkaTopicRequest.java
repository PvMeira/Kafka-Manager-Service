package com.kenai.br.kafka.admin.service.dto.external.topics;

import com.kenai.br.kafka.admin.service.dto.CustomProps;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaTopicRequest {

    private String topic_name;
    private Long partitions_count;
    private Long replication_factor;
    private List<CustomProps> configs;
    private List<KafkaTopicReplicaAssignment> replicas_assignments;


}
