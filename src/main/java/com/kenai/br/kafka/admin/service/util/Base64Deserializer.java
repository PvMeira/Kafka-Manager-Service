package com.kenai.br.kafka.admin.service.util;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Base64;

public class Base64Deserializer implements Deserializer<String> {

    @Override
    public String deserialize(String s, byte[] bytes) {
        try {
           return Base64.getEncoder().encodeToString(bytes);
        }catch (Exception var4) {
            throw new SerializationException("Error when deserializing byte[] to string due to unsupported encoding ");

        }
    }

}
