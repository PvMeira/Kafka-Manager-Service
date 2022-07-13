package com.kenai.br.kafka.admin.service.dto.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kenai.br.kafka.admin.service.enumerate.UrlTestType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigurationTestConnectionRequest {

    private String serverUrl;
    private UrlTestType type;
}
