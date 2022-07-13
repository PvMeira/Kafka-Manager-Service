package com.kenai.br.kafka.admin.service.dto.brokers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrokersResponse {

    private Long brokers;
    private String controller;
    private String version;
    private String similarConfig;

    private List<BrokerResponse> data;

}
