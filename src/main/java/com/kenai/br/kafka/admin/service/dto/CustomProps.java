package com.kenai.br.kafka.admin.service.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class CustomProps {
    private String name;
    private String value;
}
