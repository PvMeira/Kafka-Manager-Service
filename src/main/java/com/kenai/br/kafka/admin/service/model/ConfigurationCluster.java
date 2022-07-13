package com.kenai.br.kafka.admin.service.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
@Entity(name = "CONFIGURATION")
public class ConfigurationCluster {

    @Id
    @Column(name = "CLUSTER_NAME", nullable = false)
    private String clusterName;

    @Column(name = "BOOTSTRAP_SERVER", nullable = false)
    private String bootstrapServer;

    @Column(name = "ZOOKEEPER")
    private String zookeeper;

    @Column(name = "ADDITIONAL_PROPERTIES")
    private String additionalProperties;

    @Column(name = "COLOR")
    private String color;

    @Column(name = "SCHEMA_VERSION")
    private String schemaVersion;

    @Column(name = "SCHEMA_REGISTRY_URL")
    private String schemaRegistryUrl;

    @Column(name = "SCHEMA_REGISTRY_SECURITY_TYPE")
    private String schemaRegistrySecurityType;

    @Column(name = "SCHEMA_REGISTRY_SECURITY_USER")
    private String schemaRegistrySecurityUser;

    @Column(name = "SCHEMA_REGISTRY_SECURITY_PASS")
    private String schemaRegistrySecurityPass;

    @Column(name = "SCHEMA_REGISTRY_SECURITY_TOKEN")
    private String schemaRegistrySecurityToken;

    @Column(name = "SCHEMA_REGISTRY_ADDITIONAL_PROPERTIES")
    private String schemaRegistryAdditionalProperties;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConfiguration")
    private List<ConfigurationKafkaConnect> kafkaConnects;

    @Column(name = "USE_CONFLUENT_API")
    private boolean useConfluentApi;

    @Column(name = "URL_CONFLUENT_REST_SERVER")
    private String urlConfluentRestServer;
}
