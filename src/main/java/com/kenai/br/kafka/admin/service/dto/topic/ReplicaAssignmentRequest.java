package com.kenai.br.kafka.admin.service.dto.topic;

import com.kenai.br.kafka.admin.service.dto.external.topics.KafkaTopicReplicaAssignment;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class ReplicaAssignmentRequest {
    private Long id;
    private List<Long>brokerIds = new ArrayList<>(0);


    public KafkaTopicReplicaAssignment toEntity() {
        return KafkaTopicReplicaAssignment.builder()
                .withPartition_id(this.id)
                .withBroker_ids(this.brokerIds).build();
    }

    public List<Integer> getBrokerIdsInt() {
        return this.getBrokerIds().stream().mapToInt(Long::intValue).boxed().collect(Collectors.toList());
    }

    public Integer getIdInt() {
        return this.getId().intValue();
    }
}
