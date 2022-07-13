package com.kenai.br.kafka.admin.service.dto.topic;

import com.kenai.br.kafka.admin.service.dto.CustomProps;
import com.kenai.br.kafka.admin.service.dto.external.topics.KafkaTopicRequest;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class TopicRequest {

    private String topicName;
    private Long partitionsCount;
    private Long replicationFactor;
    private List<CustomProps> configs;
    private List<ReplicaAssignmentRequest> replicasAssignments;


    public KafkaTopicRequest toEntity() {
        return KafkaTopicRequest.builder()
                .withTopic_name(this.topicName)
                .withPartitions_count(this.partitionsCount)
                .withReplication_factor(this.replicationFactor)
                .withConfigs(this.configs)
                .withReplicas_assignments(this.replicasAssignments == null ? null : this.replicasAssignments.stream().map(ReplicaAssignmentRequest::toEntity).collect(Collectors.toList()))
                .build();
    }


}
