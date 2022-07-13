# kafka-admin


# Starting Local Kafka Environment

* curl -O http://packages.confluent.io/archive/7.0/confluent-community-7.0.1.tar.gz
* tar xzf confluent-7.0.1.tar.gz

* ./zookeeper-server-start ../etc/kafka/zookeeper.properties
* ./kafka-server-start ../etc/kafka/server.properties 
* ./schema-registry-start ../etc/schema-registry/schema-registry.properties
* ./kafka-rest-start ../etc/kafka-rest/kafka-rest.properties

This is the Kafka V3, using Json