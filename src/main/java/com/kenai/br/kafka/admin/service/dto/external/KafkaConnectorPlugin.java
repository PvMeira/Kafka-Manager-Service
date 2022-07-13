package com.kenai.br.kafka.admin.service.dto.external;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class KafkaConnectorPlugin {

    @SerializedName("class")
    private String className;
    private String type;
    private String version;

}
