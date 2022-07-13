package com.kenai.br.kafka.admin.service.dto.external;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaBrokers {
    private String kind;
    private List<KafkaBroker> data;
}
