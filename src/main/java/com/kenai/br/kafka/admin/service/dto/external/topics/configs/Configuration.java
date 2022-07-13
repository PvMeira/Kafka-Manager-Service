package com.kenai.br.kafka.admin.service.dto.external.topics.configs;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class Configuration {

    private String kind;
    private String cluster_id;
    private String name;
    private String value;
    private boolean is_read_only;
    private boolean is_sensitive;
    private String source;
    private String topic_name;
    private boolean is_default;
    private List<Synonyms> synonyms;

}
