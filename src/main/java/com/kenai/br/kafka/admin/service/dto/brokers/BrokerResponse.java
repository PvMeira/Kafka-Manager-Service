package com.kenai.br.kafka.admin.service.dto.brokers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.dto.external.KafkaBroker;
import lombok.*;

import java.util.Map;


@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrokerResponse {
    private String kind;
    private Long broker_id;
    private String host;
    private Long port;
    private String rack;
    private Map<String, String> configs;

    public static BrokerResponse parse(KafkaBroker k) {
        return BrokerResponse
                .builder()
                .withBroker_id(k.getBroker_id())
                .withKind(k.getKind())
                .withHost(k.getHost())
                .withPort(k.getPort())
                .withRack(k.getRack())
                .withConfigs(k.getConfigs())
                .build();
    }

    public String getUrl() {
        return this.host + ":" + this.port;
    }

}
