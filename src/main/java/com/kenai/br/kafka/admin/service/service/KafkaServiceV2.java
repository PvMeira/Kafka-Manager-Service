package com.kenai.br.kafka.admin.service.service;

import com.kenai.br.kafka.admin.service.dto.external.KafkaCluster;
import com.kenai.br.kafka.admin.service.exception.ApiException;
import com.kenai.br.kafka.admin.service.exception.WebClientException;
import com.kenai.br.kafka.admin.service.model.ConfigurationCluster;
import com.kenai.br.kafka.admin.service.repository.ConfigurationClusterRepository;
import com.kenai.br.kafka.admin.service.util.Base64Serializer;
import com.kenai.br.kafka.admin.service.util.GsonSerializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.java.Log;
import lombok.var;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Log
@Service
public abstract class KafkaServiceV2 extends DefaultService {

    private static final Integer TIMEOUT = 15000;
    private static final String CONTENT_TYPE_NAME = "Content-Type";
    protected final String RUNNING = "RUNNING";
    protected final String FAILED = "FAILED";

    @Autowired
    private ConfigurationClusterRepository confRepository;

    protected MediaType getMediaType(String mediaType) {
        return MediaType.parseMediaType(mediaType);
    }

    private HttpClient createHttpClient() {
        return HttpClient.create()
                         .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                         .responseTimeout(Duration.ofMillis(TIMEOUT))
                         .doOnConnected(conn ->
                                        conn.addHandlerLast(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
                                            .addHandlerLast(new WriteTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS)));
    }

    private WebClient creteWebClient(final String url) {
        return WebClient.builder()
                        .clientConnector(new ReactorClientHttpConnector(this.createHttpClient()))
                        .baseUrl(url)
                        .build();
    }

    protected AdminClient getAdminClient(String server) {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        return AdminClient.create(config);
    }

    /**
     * Will handle the response, inside each WebClient http call, in case of any error
     * will throws a WebClientException containg the raw body and status Code from the response.
     *
     * @return Function<ClientResponse, Mono<String>>
     */
    private Function<ClientResponse, Mono<String>> handleResponseMono() {
        return response -> {
            if (response.statusCode().isError()) {
                return response.createException()
                               .flatMap(a -> Mono.error(new WebClientException(  a.getResponseBodyAsString()
                                                                               , a.getRawStatusCode()
                                                                               , "A error has return from the call to " + a.getRequest().getURI())));
            }
            return response.bodyToMono(String.class);
        };
    }

    protected String getClusterIDTemp(String uri, String version) throws WebClientException {
        String url = this.cleanUrl(uri) + this.cleanUrl(version) + "clusters/";

        var response = (String)get(url);

        var kafkaCluster = (KafkaCluster) super.gson().fromJson(response, KafkaCluster.class);

        return kafkaCluster.getData().get(0).getCluster_id();
    }

    public String get(String url) throws WebClientException {
        return  this.creteWebClient(url)
                .get()
                .exchangeToMono(this.handleResponseMono())
                .block();
    }

    public String getSchemaVersion(String confName) {
        ConfigurationCluster configurationCluster = this.confRepository.findById(confName).orElseThrow(() -> new ApiException("Conf was not found"));
        return configurationCluster.getSchemaVersion();
    }

    public String get(String confName, String url) throws WebClientException {
        return this.get(confName, url, MediaType.APPLICATION_JSON, false);
    }

    public String get(String confName, String url, boolean isKafkaConnector) throws WebClientException {
        return this.get(confName, url, null, isKafkaConnector);
    }

    public String get(String confName, String url, MediaType mediaType, boolean isKafkaConnector) throws WebClientException {
        WebClient.RequestHeadersSpec<?> headersSpec = this.creteWebClient(this.getUrlFromConf(confName, isKafkaConnector))
                                                          .get()
                                                          .uri(this.cleanUrl(url));
        if (null == mediaType) {
            return headersSpec.exchangeToMono(this.handleResponseMono()).block();
        }
        return headersSpec.header(CONTENT_TYPE_NAME, mediaType.toString())
                          .exchangeToMono(this.handleResponseMono())
                          .block();
    }

    public String delete(String confName, String url) throws WebClientException {
        return this.delete(confName, url, MediaType.APPLICATION_JSON, false);
    }

    public String delete(String confName, String url, boolean isKafkaConnector) throws WebClientException {
        return this.delete(confName, url, MediaType.APPLICATION_JSON, isKafkaConnector);
    }

    public String delete(String confName, String url,MediaType mediaType, boolean isKafkaConnector) throws WebClientException {
        return this.creteWebClient(this.getUrlFromConf(confName, isKafkaConnector))
                   .delete()
                   .uri(this.cleanUrl(url))
                   .header(CONTENT_TYPE_NAME, mediaType.toString())
                   .exchangeToMono(this.handleResponseMono())
                   .block();
    }

    public String post(String confName, String url,Object body) throws WebClientException {
        return this.post(confName, url, body, MediaType.APPLICATION_JSON, false);
    }

    public String post(String confName, String url,Object body, boolean isKafkaConnector) throws WebClientException {
        return this.post(confName, url, body, MediaType.APPLICATION_JSON, isKafkaConnector);
    }

    public String post(String confName, String url,Object body, MediaType mediaType, boolean isKafkaConnector) throws WebClientException {
        return this.creteWebClient(this.getUrlFromConf(confName, isKafkaConnector))
                   .post()
                   .uri(this.cleanUrl(url))
                   .contentType(mediaType)
                   .bodyValue(super.gson().toJson(body))
                   .exchangeToMono(this.handleResponseMono())
                   .block();
    }

    public String put(String confName, String url,Object body) throws WebClientException {
        return this.put(confName, url, body, MediaType.APPLICATION_JSON, false);
    }

    public String put(String confName, String url,Object body, boolean isKafkaConnector) throws WebClientException {
        return this.put(confName, url, body, MediaType.APPLICATION_JSON, isKafkaConnector);
    }

    public String put(String confName, String url,Object body, MediaType mediaType, boolean isKafkaConnector) throws WebClientException {
        return this.creteWebClient(this.getUrlFromConf(confName, isKafkaConnector))
                   .put()
                   .uri(this.cleanUrl(url))
                   .contentType(mediaType)
                   .bodyValue(super.gson().toJson(body))
                   .exchangeToMono(this.handleResponseMono())
                   .block();
    }


    /**
     * Will return the base url to be used, in case of being the Kafka Connect url, isKafkaConnector must be true,
     *  or else the return url, will be the boostrap server url from the configuration
     *
     * @param confName
     * @param isKafkaConnector
     * @return
     */
    private String getUrlFromConf(final String confName, boolean isKafkaConnector) {
        ConfigurationCluster configurationCluster = this.confRepository.findById(confName)
                                                                       .orElseThrow(() -> new ApiException("Conf was not found"));
        String url = configurationCluster.getUrlConfluentRestServer();
        if (isKafkaConnector) {
            url = configurationCluster.getKafkaConnects()
                                      .stream()
                                      .findFirst()
                                      .orElseThrow(() -> new ApiException("No connector conf was found"))
                                      .getUrl();
        }
        return url;
    }

    protected String getSerializer(String type) {
        if ("json".equalsIgnoreCase(type))
            return GsonSerializer.class.getName();

        if ("base64".equalsIgnoreCase(type))
            return Base64Serializer.class.getName();

        return StringSerializer.class.getName();

    }

}
