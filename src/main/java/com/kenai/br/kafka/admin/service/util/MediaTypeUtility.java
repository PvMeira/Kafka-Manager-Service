package com.kenai.br.kafka.admin.service.util;

import org.springframework.http.MediaType;

public interface MediaTypeUtility {

    MediaType APPLICATION_VND_KAFKA_JSON_v2JSON = MediaType.parseMediaType("application/vnd.kafka.json.v2+json");
    MediaType APPLICATION_VND_KAFKA_BINARY_v2JSON = MediaType.parseMediaType("application/vnd.kafka.binary.v2+json");
    MediaType APPLICATION_VND_KAFKA_AVRO_v2JSON = MediaType.parseMediaType("application/vnd.kafka.avro.v2+json");
    MediaType APPLICATION_VND_KAFKA_PROTOBUF_v2JSON = MediaType.parseMediaType("application/vnd.kafka.protobuf.v2+json");
}
