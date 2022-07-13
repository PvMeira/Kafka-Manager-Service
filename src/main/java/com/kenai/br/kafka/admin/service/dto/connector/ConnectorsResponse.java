package com.kenai.br.kafka.admin.service.dto.connector;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConnectorsResponse {
    private Long instance;
    private Long running;
    private Long failed;
    private Long tasksRunning;
    private Long tasksFailed;

    private List<ConnectorResponse> data;



}
