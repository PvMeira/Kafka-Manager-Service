package com.kenai.br.kafka.admin.service.dto.consumergroups;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumerGroupsGenerlResponse {

    private Long active;
    private Long empty;
    private Long rebalancing;
    private Long dead;
    private List<ConsumerGroupsResponse> data;
    
}
