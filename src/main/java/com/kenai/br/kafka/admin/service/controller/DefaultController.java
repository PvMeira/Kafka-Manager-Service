package com.kenai.br.kafka.admin.service.controller;

import com.kenai.br.kafka.admin.service.dto.HeaderRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class DefaultController {
    public final static String HEADER_NAME_CLUSTER_ID = "kmd_cluster_id";
    public final static String HEADER_NAME_CONF_NAME = "kmd_conf_name";

    @Autowired
    private HttpServletRequest request;

    protected String getClusterId() {
        return this.request.getHeader(HEADER_NAME_CLUSTER_ID);
    }
    protected String getConfName() {
        return this.request.getHeader(HEADER_NAME_CONF_NAME);
    }

    protected HeaderRequest getHeader() {
        return HeaderRequest
                .builder()
                .withClusterID(getClusterId())
                .withConfigurationName(getConfName())
                .build();
    }

    protected String getHeaderContentType() {
        return this.request.getContentType();
    }
}
