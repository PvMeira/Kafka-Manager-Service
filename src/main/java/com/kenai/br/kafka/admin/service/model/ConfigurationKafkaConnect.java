package com.kenai.br.kafka.admin.service.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@Entity(name = "KAFKA_CONNECT")
public class ConfigurationKafkaConnect {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "URL", nullable = false)
    private String url;

    @Column(name = "HTTP_HEADERS")
    private String httpHeaders;

    @Column(name = "SECURITY_TYPE")
    private String securityType;

    @Column(name = "SECURITY_USER")
    private String securityUser;

    @Column(name = "SECURITY_PASS")
    private String securityPass;

    @Column(name = "SECURITY_TOKEN")
    private String securityToken;

    @Column(name = "ADDITIONAL_PROPERTIES")
    private String additionalProperties;

    @Column(name = "ID_CONFIGURATION")
    private String idConfiguration;
}
