package com.kenai.br.kafka.admin.service.dto.topic.configs;

import com.kenai.br.kafka.admin.service.dto.external.topics.configs.Configuration;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class ConfigurationResponse {
    private String kind;
    private String clusterId;
    private String name;
    private String value;
    private boolean isReadOnly;
    private boolean isSensitive;
    private String source;
    private String topicName;
    private boolean isDefault;
    private List<ConfigurationSynonymsResponse> synonyms;

    public static ConfigurationResponse parse(Configuration p) {
        return ConfigurationResponse.builder()
                .withKind(p.getKind())
                .withClusterId(p.getCluster_id())
                .withName(p.getName())
                .withValue(p.getValue())
                .withIsReadOnly(p.is_read_only())
                .withIsSensitive(p.is_sensitive())
                .withSource(p.getSource())
                .withTopicName(p.getTopic_name())
                .withIsDefault(p.is_default())
                .withSynonyms(p.getSynonyms().stream().map(ConfigurationSynonymsResponse::parse).collect(Collectors.toList())).build();
    }
}
