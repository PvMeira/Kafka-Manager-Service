package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaCluster {
    private String kind;
    private String cluster_id;
    private List<KafkaCluster> data;
}
