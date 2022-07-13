package com.kenai.br.kafka.admin.service.dto.external.topics.configs;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class Synonyms {

    private String name;
    private String value;
    private String source;
}
