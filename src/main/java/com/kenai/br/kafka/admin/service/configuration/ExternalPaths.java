package com.kenai.br.kafka.admin.service.configuration;

public interface ExternalPaths {
    String CONNECTORS_BASE = "/connectors/";
    String CONNECTORS_CONFIG = CONNECTORS_BASE + "/%s/config";
    String CONNECTOR_STATUS = CONNECTORS_BASE + "/%s/status";
    String CONNECTORS_BASE_WITH_EXPAND_INFO_STATUS = "/connectors?expand=status&expand=info";
    String CONNECTOR_PLUGINS = "/connector-plugins";
    String CONNECTOR_TOPICS = CONNECTORS_BASE + "%s/topics";
    String CONNECTORS_ACTION_PAUSE = CONNECTORS_BASE + "/%s/pause";
    String CONNECTORS_ACTION_RESUME = CONNECTORS_BASE + "/%s/resume";
    String CONNECTORS_ACTION_RESTART = CONNECTORS_BASE + "/%s/restart";


    String CLUSTER_BASE = "/%s/clusters";
    String CLUSTER_BASE_WITH_ID = CLUSTER_BASE + "/%s";
    String BROKERS_BASE = CLUSTER_BASE + "/%s/brokers";
    String BROKERS_CONFIG = BROKERS_BASE + "/%s/configs";

    String SCHEMA_REGISTRY_BASE = "/schemas";

    String CONSUMER_GROUPS_BASE = CLUSTER_BASE + "/%s/consumer-groups";
    String CONSUMER_GROUPS_FIND_BASE = CONSUMER_GROUPS_BASE + "/%s/consumers";
    String CONSUMER = "/consumers/%s";
    String CONSUMER_SUBSCRIPTION = CONSUMER + "/instances/%s/subscription";
    String CONSUMER_RECORDS = CONSUMER + "/instances/%s/records";



    String TOPIC_BASE = CLUSTER_BASE_WITH_ID + "/topics";
    String TOPIC_FIND = TOPIC_BASE + "/%s";
    String TOPIC_DELETE = TOPIC_BASE + "/%s";
    String TOPIC_CONFIGS = TOPIC_BASE + "/%s/configs";
    String TOPIC_PARTITIONS = "/topics/%s/partitions";
    String TOPIC_PRODUCE = "/topics/%s";
    String TOPIC_PRODUCE_PARTITION = TOPIC_PARTITIONS + "/%s";




}
