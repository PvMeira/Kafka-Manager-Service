package com.kenai.br.kafka.admin.service.dto.connector;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectorStatusConnectorResponse {

    private String state;
    private String worker_id;
    private String trace;
}
