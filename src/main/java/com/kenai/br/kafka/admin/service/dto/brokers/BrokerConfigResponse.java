package com.kenai.br.kafka.admin.service.dto.brokers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.dto.Synonyms;
import com.kenai.br.kafka.admin.service.dto.external.KafkaBrokerConfig;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrokerConfigResponse {

    private String kind;
    private String clusterId;
    private String name;
    private String value;
    private boolean isReadOnly;
    private boolean isSensitive;
    private boolean isDefault;
    private String soruce;
    private List<Synonyms> synonyms;


    public static BrokerConfigResponse parse(KafkaBrokerConfig k) {
        return BrokerConfigResponse
               .builder()
                .withClusterId(k.getCluster_id())
                .withKind(k.getKind())
                .withName(k.getName())
                .withValue(k.getValue())
                .withIsReadOnly(k.is_read_only())
                .withIsSensitive(k.is_sensitive())
                .withIsDefault(k.is_default())
                .withSoruce(k.getSoruce())
                .withSynonyms(k.getSynonyms().stream().map(Synonyms::parse).collect(Collectors.toList()))
               .build();
    }
}
