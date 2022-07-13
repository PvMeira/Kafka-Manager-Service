package com.kenai.br.kafka.admin.service.dto.topic.partitions;

import com.kenai.br.kafka.admin.service.dto.external.topics.Partitions;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class PartitionsResponse {

    private Long partition;
    private Long leader;
    private List<PartitionsReplicaResponse> replicas;


    public static PartitionsResponse parse(Partitions p) {
        return PartitionsResponse.builder()
                                 .withPartition(p.getPartition())
                                 .withLeader(p.getLeader())
                                 .withReplicas(p.getReplicas().stream().map(PartitionsReplicaResponse::parse).collect(Collectors.toList()))
                                 .build();
    }
}
