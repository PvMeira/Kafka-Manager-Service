package com.kenai.br.kafka.admin.service.dto.consumergroups;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.dto.external.KafkaConsumerGroup;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumerGroupsResponse {
    private String consumerGroupId;
    private String consumerId;
    private String state;
    private Long members;
    private Long maxLag;
    private Long totalLag;
    private Long partitions;


    public static ConsumerGroupsResponse parse(KafkaConsumerGroup k) {
        return ConsumerGroupsResponse.builder()
                .withConsumerGroupId(k.getConsumer_group_id())
                .withState(k.getState())
                .build();
    }
}
