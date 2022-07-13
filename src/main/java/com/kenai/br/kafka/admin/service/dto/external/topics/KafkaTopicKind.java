package com.kenai.br.kafka.admin.service.dto.external.topics;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaTopicKind {
    private String kind;
    private List<KafkaTopic> data;
}
