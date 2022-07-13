package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaRelated {
    private String related;
}
