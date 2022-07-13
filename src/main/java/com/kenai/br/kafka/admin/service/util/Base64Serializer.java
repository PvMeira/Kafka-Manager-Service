package com.kenai.br.kafka.admin.service.util;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Base64;

public class Base64Serializer implements Serializer<String> {

    @Override
    public byte[] serialize(String s, String s2) {
        return  Base64.getEncoder().encodeToString(s2.getBytes()).getBytes();
    }
}
