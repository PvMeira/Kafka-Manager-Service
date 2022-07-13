package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaBrokerConfig {
    private String kind;
    private String cluster_id;
    private String name;
    private String value;
    private boolean is_read_only;
    private boolean is_sensitive;
    private boolean is_default;
    private String soruce;
    private List<KafkaSynonyms> synonyms;
}
