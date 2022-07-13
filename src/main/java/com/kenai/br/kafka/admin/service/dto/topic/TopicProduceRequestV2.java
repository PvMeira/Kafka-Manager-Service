package com.kenai.br.kafka.admin.service.dto.topic;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class TopicProduceRequestV2 {

    private String keySerializer;
    private String valueSerializer;

    private Object keyValue;
    private Object value;
}
