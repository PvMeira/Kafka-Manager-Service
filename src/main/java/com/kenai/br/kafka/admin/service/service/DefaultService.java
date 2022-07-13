package com.kenai.br.kafka.admin.service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@Log
@Service
public class DefaultService {

    protected Gson gson() {
        return new GsonBuilder().create();
    }

    protected WebClient createWc(String baseURl) {
        return  WebClient.create(baseURl);
    }

    protected String cleanUrl(String url) {
        if(url.endsWith("/")) return url;
        if (url.contains("?")) return url;
        return url + "/";
    }

    protected boolean isWinodws() {
        return System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

}
