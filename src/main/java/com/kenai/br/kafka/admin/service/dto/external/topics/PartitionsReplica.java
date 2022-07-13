package com.kenai.br.kafka.admin.service.dto.external.topics;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class PartitionsReplica {
    private Long broker;
    private boolean leader;
    private boolean in_sync;
}
