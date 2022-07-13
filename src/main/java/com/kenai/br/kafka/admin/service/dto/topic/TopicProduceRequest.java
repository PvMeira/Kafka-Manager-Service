package com.kenai.br.kafka.admin.service.dto.topic;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class TopicProduceRequest {

    private String contentType;
    private Object data;
}
