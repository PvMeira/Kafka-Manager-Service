package com.kenai.br.kafka.admin.service.dto.topic;

import com.kenai.br.kafka.admin.service.dto.external.topics.Offset;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class OffsetResponse {

    private Long partition;
    private Long offset;
    private String errorCode;
    private String error;


    public static OffsetResponse parse(Offset offset) {
        return OffsetResponse.builder()
                             .withOffset(offset.getOffset())
                             .withError(offset.getError())
                             .withErrorCode(offset.getError_code())
                             .withPartition(offset.getPartition())
                             .build();
    }
}
