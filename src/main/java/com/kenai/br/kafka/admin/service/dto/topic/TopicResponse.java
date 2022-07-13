package com.kenai.br.kafka.admin.service.dto.topic;

import com.kenai.br.kafka.admin.service.dto.external.topics.KafkaTopic;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class TopicResponse {
    private String kind;
    private String clusterId;
    private String topicName;
    private String isInternal;
    private Long replicationFactor;
    private Long partitionsCount;


    public static TopicResponse parse(KafkaTopic t) {
        return TopicResponse.builder()
                .withKind(t.getKind())
                .withClusterId(t.getCluster_id())
                .withTopicName(t.getTopic_name())
                .withIsInternal(t.getIs_internal())
                .withReplicationFactor(t.getReplication_factor())
                .withPartitionsCount(t.getPartitions_count())
                .build();
    }
}
