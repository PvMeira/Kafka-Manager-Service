package com.kenai.br.kafka.admin.service.dto.topic.configs;

import com.kenai.br.kafka.admin.service.dto.external.topics.configs.Synonyms;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class ConfigurationSynonymsResponse {
    private String name;
    private String value;
    private String source;

    public static ConfigurationSynonymsResponse parse(Synonyms r) {
        return ConfigurationSynonymsResponse.builder()
                                            .withName(r.getName())
                                            .withValue(r.getValue())
                                            .withSource(r.getSource()).build();
    }
}
