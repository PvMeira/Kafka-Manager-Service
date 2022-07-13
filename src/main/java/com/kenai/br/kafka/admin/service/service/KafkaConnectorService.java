package com.kenai.br.kafka.admin.service.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kenai.br.kafka.admin.service.configuration.ExternalPaths;
import com.kenai.br.kafka.admin.service.dto.*;
import com.kenai.br.kafka.admin.service.dto.connector.*;
import com.kenai.br.kafka.admin.service.dto.core.ConfigurationTestConnectionRequest;
import com.kenai.br.kafka.admin.service.dto.external.*;
import com.kenai.br.kafka.admin.service.enumerate.RuntineStatusType;
import lombok.extern.java.Log;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log
@Service
public class KafkaConnectorService extends KafkaServiceV2 {

    public ConnectorsResponse getAllConnectors(HeaderRequest request) {
        var response = (String) super.get(  request.getConfigurationName()
                                          , ExternalPaths.CONNECTORS_BASE_WITH_EXPAND_INFO_STATUS
                                          , true);
        Set<Map.Entry<String, JsonElement>> entrySet = this.gson().fromJson(response, JsonObject.class).entrySet();
        var info = entrySet.stream()
                                                  .map(jsonElement -> this.gson()
                                                          .fromJson(jsonElement.getValue()
                                                                          .getAsJsonObject()
                                                                          .get("info")
                                                                          .toString()
                                                                  , KafkaConnectorInfo.class))
                                                  .filter(a-> a!=null)
                                                  .collect(Collectors.toList());
        var status = entrySet.stream()
                .map(jsonElement -> this.gson().fromJson(  jsonElement.getValue()
                                                                      .getAsJsonObject()
                                                                      .get("status")
                                                                      .toString()
                                                         , KafkaConnectorStatus.class))
                .collect(Collectors.toList());
        var connectorTasks = new ArrayList<KafkaConnectorTask>();
        status.stream().forEach(a -> connectorTasks.addAll(a.getTasks()));
        return ConnectorsResponse.builder()
                .withInstance(1l)
                .withRunning(status.stream()
                        .filter(a -> a.getConnector()
                                .getState()
                                .equalsIgnoreCase(RUNNING))
                        .count())
                .withFailed(status.stream()
                        .filter(a -> a.getConnector()
                                .getState()
                                .equalsIgnoreCase(FAILED))
                        .count())
                .withTasksRunning(connectorTasks.stream()
                        .filter(a -> a.getState().equalsIgnoreCase(RUNNING))
                        .count())
                .withTasksFailed(connectorTasks.stream()
                        .filter(a -> a.getState().equalsIgnoreCase(FAILED))
                        .count())
                .withData(info.stream().map(ConnectorResponse::buildExternalToResponse).collect(Collectors.toList()))
                .build();
    }

    public ConnectorResponse getConnector(String name, HeaderRequest request) {
        var  response=  (String) super.get(request.getConfigurationName(),ExternalPaths.CONNECTORS_BASE + name, true);
        return super.gson().fromJson(response, ConnectorResponse.class);
    }

    public ConnectorStatusResponse getConnectorStatus(String name, HeaderRequest request) {
        var response =  (String) super.get(request.getConfigurationName(),String.format(ExternalPaths.CONNECTOR_STATUS, name), true);
        return super.gson().fromJson(response, ConnectorStatusResponse.class);
    }

    public void saveConnector(ConnectorRequest body, HeaderRequest request) {
        super.post(request.getConfigurationName(),ExternalPaths.CONNECTORS_BASE, body, true);
    }

    public void updateConfigConnector(String name, ConnectorRequest body, HeaderRequest request) {
        super.put(request.getConfigurationName(),String.format(ExternalPaths.CONNECTORS_CONFIG, name), body.getConfig(), true);
    }

    public void deleteConnector(String name, HeaderRequest request) {
        super.delete(request.getConfigurationName(),ExternalPaths.CONNECTORS_BASE + name, true);
    }

    public void testConnection(ConfigurationTestConnectionRequest request) {
        super.get(request.getServerUrl());
    }

    public List<ConnectorPluginResponse> getPlugins(HeaderRequest request) {
        var response = (String) super.get(request.getConfigurationName(), ExternalPaths.CONNECTOR_PLUGINS, true);
        List<KafkaConnectorPlugin> list = super.gson().fromJson(response, new TypeToken<ArrayList<KafkaConnectorPlugin>>(){}.getType());
        return list.stream().map(ConnectorPluginResponse::parse).collect(Collectors.toList());
    }

    public List<String> getTopicsFromConnector(HeaderRequest request, String connectorName) {
        var response = (String) super.get(request.getConfigurationName(),String.format(ExternalPaths.CONNECTOR_TOPICS, connectorName), true);
        JsonObject object = super.gson().fromJson(response, JsonObject.class);
        return super.gson().fromJson(object.get(connectorName).getAsJsonObject().get("topics").toString(), new TypeToken<ArrayList<String>>(){}.getType());
    }

    public void changeRuntimeStatus(RuntineStatusType type, String name, HeaderRequest request) {
        switch (type) {
            case PAUSE:
                super.put(  request.getConfigurationName()
                          , String.format(ExternalPaths.CONNECTORS_ACTION_PAUSE, name)
                          , null
                          , true);
                break;
            case RESUME:
                super.put(  request.getConfigurationName()
                          , String.format(ExternalPaths.CONNECTORS_ACTION_RESUME, name)
                          , null
                          , true);
                break;
            case RESTART:
                super.post(  request.getConfigurationName()
                           , String.format(ExternalPaths.CONNECTORS_ACTION_RESTART, name)
                           , null
                           , true);
                break;
        }

    }

}
