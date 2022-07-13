package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaSynonyms {

    private String name;
    private String value;
    private String source;
}
