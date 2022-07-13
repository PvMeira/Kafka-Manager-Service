package com.kenai.br.kafka.admin.service.dto.consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.dto.external.KafkaConsumer;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumerResponse {

    private String consumerGroupId;
    private String consumerId;
    private String instanceId;
    private String clientId;


    public static ConsumerResponse parse(KafkaConsumer c) {
        return ConsumerResponse.builder()
                .withConsumerGroupId(c.getConsumer_group_id())
                .withConsumerId(c.getConsumer_id())
                .withInstanceId(c.getInstance_id())
                .withClientId(c.getClient_id())
                .build();
    }
}
