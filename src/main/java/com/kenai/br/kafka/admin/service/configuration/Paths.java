package com.kenai.br.kafka.admin.service.configuration;

public interface Paths {
    String CORE_BASE = "/core";
    String CONNECTORS_URL_BASE = "/connectors";
    String CORE_CONSUMER = "/consumer";
    String TOPICS_URL_BASE = "/topics";
    String BROKERS_URL_BASE = "/brokers";
    String CORE_URL_TEST_BASE = CORE_BASE + "/url-test";
    String CORE_CONFIGURATION_BASE = CORE_BASE + "/configuration";
    String CORE_KAFKA_CONNECT_BASE = CORE_CONFIGURATION_BASE + "/kafka-connect";
    String CORE_CONSUMER_GROUPS = "/consumer-groups";
}
