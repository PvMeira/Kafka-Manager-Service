package com.kenai.br.kafka.admin.service.dto.topic;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class RecordsRequest {
    private String key;
    private String value;
}
