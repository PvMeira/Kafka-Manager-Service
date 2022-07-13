package com.kenai.br.kafka.admin.service.dto.consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsumerRequest {

    private HashMap<String, String>props;
    private List<String>topics;
}
