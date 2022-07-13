package com.kenai.br.kafka.admin.service.dto.external.topics;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaTopicReplicaAssignment {

    private Long partition_id;
    private List<Long> broker_ids;
}
