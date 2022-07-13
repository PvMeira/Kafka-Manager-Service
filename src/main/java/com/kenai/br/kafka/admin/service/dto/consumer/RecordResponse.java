package com.kenai.br.kafka.admin.service.dto.consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordResponse {
    private String topic;
    private String key;
    private String value;
    private Long partition;
    private Long offset;
}
