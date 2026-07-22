package com.fx.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;

@Component
public class OrchestratorClient {

    private final RestTemplate http;
    private final String baseUrl;

    public OrchestratorClient(RestTemplateBuilder builder,
                              @Value("${fx.orchestrator.url}") String baseUrl) {
        this.http = builder
                .setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(3))
                .build();
        this.baseUrl = baseUrl;
    }

    public void ack(long batchId, String status) {
        try {
            String url = baseUrl + "/api/feed/ack";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> body = Map.of(
                    "batchId", batchId,
                    "status", status
            );
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            http.exchange(url, HttpMethod.POST, entity, Void.class);
        } catch (Exception e) {
            // A callback that throws must never break the request we are serving.
        }
    }
}
