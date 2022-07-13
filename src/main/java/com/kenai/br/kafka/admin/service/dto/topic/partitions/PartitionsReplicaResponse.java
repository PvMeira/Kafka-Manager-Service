package com.kenai.br.kafka.admin.service.dto.topic.partitions;

import com.kenai.br.kafka.admin.service.dto.external.topics.PartitionsReplica;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class PartitionsReplicaResponse {

    private Long broker;
    private boolean leader;
    private boolean inSync;


    public static PartitionsReplicaResponse parse(PartitionsReplica r) {
        return PartitionsReplicaResponse.builder()
                                        .withBroker(r.getBroker())
                                        .withLeader(r.isLeader())
                                        .withInSync(r.isIn_sync())
                                        .build();
    }
}
