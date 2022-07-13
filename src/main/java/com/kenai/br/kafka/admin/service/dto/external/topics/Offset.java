package com.kenai.br.kafka.admin.service.dto.external.topics;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class Offset {

    private Long partition;
    private Long offset;
    private String error_code;
    private String error;
}
