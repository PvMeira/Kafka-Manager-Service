package com.kenai.br.kafka.admin.service.dto.topic;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class TopicProduceResponse {

    private List<OffsetResponse> offsets;
    private String keySchemaId;
    private String valueSchemaId;


    public static TopicProduceResponse parse(com.kenai.br.kafka.admin.service.dto.external.topics.TopicProduceResponse t) {
        return TopicProduceResponse.builder()
                                   .withKeySchemaId(t.getKey_schema_id())
                                   .withValueSchemaId(t.getValue_schema_id())
                                   .withOffsets(t.getOffsets().stream().map(OffsetResponse::parse).collect(Collectors.toList()))
                                   .build();
    }
}
