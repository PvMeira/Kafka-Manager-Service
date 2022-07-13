package com.kenai.br.kafka.admin.service.dto.external.topics;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class TopicProduceResponse {

    private List<Offset> offsets;
    private String key_schema_id;
    private String value_schema_id;
}
