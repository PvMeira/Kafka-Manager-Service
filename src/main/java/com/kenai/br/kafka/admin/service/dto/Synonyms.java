package com.kenai.br.kafka.admin.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.dto.external.KafkaSynonyms;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Synonyms {

    private String name;
    private String value;
    private String source;


    public static Synonyms parse(KafkaSynonyms k) {
        return Synonyms.builder()
                .withName(k.getName())
                .withValue(k.getValue())
                .withSource(k.getSource())
                .build();
    }
}
