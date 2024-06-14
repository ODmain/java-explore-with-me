package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.StatisticsInputHitDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsClient extends BaseClient {

    @Autowired
    public StatisticsClient(@Value("${statistics-service.url://localhost:9090}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void createHit(StatisticsInputHitDto statisticsInputHitDto) {
        post(statisticsInputHitDto);
    }

    public ResponseEntity<Object> getStatistics(String start, String end, List<String> uris, Boolean unique) {
        String encodedStart = URLEncoder.encode(start, StandardCharsets.UTF_8);
        String encodedEnd = URLEncoder.encode(end, StandardCharsets.UTF_8);
        if (uris == null || uris.isEmpty()) {
            Map<String, Object> parameters = Map.of(
                    "start", encodedStart,
                    "end", encodedEnd,
                    "unique", unique);
            return get("/stats?start={start}&end={end}&unique={unique}", parameters);
        } else {
            Map<String, Object> parameters = Map.of(
                    "start", encodedStart,
                    "end", encodedEnd,
                    "uris", uris,
                    "unique", unique);
            return get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        }
    }
}
