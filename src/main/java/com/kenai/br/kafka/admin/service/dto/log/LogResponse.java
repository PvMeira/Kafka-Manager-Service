package com.kenai.br.kafka.admin.service.dto.log;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class LogResponse {
    private String folder;
    private String topic;
    private Integer partition;
    private Long offsetLag;
    private Long size;
}
