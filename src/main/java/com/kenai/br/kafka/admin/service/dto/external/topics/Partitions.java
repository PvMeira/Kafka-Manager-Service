package com.kenai.br.kafka.admin.service.dto.external.topics;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class Partitions {

    private Long partition;
    private Long leader;
    private List<PartitionsReplica> replicas;
}
