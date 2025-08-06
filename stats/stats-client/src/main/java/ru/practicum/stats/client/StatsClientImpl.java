package ru.practicum.stats.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.stats.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Реализация клиента для взаимодействия с сервисом статистики через REST API.
 * <p>
 * Использует {@link RestTemplate} для выполнения HTTP запросов к сервису статистики.
 * Обеспечивает сериализацию/десериализацию DTO и обработку параметров запросов.
 *
 * @see StatsClient
 * @see RestTemplate
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class StatsClientImpl implements StatsClient {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final RestTemplate rest;
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * {@inheritDoc}
     * <p>
     * Выполняет POST запрос к эндпоинту /hit сервиса статистики.
     *
     * @param endpointHitDto данные о запросе к эндпоинту
     * @throws org.springframework.web.client.RestClientException при ошибках HTTP запроса
     * @throws jakarta.validation.ConstraintViolationException    если данные запроса некорректны
     */
    @Override
    public ResponseEntity<Void> addHit(String uri, String ip) {
        EndpointHitDto hit = EndpointHitDto.builder()
                .app(applicationName)
                .timestamp(LocalDateTime.now())
                .uri(uri)
                .ip(ip)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<EndpointHitDto> request = new HttpEntity<>(hit, headers);

        return rest.exchange("/hit", HttpMethod.POST, request, Void.class);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Выполняет GET запрос к эндпоинту /stats сервиса статистики
     * с параметрами фильтрации в виде query parameters.
     *
     * @param start  дата и время начала диапазона (включительно)
     * @param end    дата и время конца диапазона (включительно)
     * @param uris   список URI для фильтрации (может быть null)
     * @param unique флаг учета только уникальных посещений по IP
     * @return список статистики по посещениям
     * @throws org.springframework.web.client.RestClientException при ошибках HTTP запроса
     */
    @Override
    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, Boolean unique) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/stats")
                .queryParam("start", start)
                .queryParam("end", end);

        if (uris != null && !uris.isEmpty()) {
            builder.queryParam("uris", String.join(",", uris));
        }

        if (unique != null) {
            builder.queryParam("unique", unique);
        }

        return rest.exchange(builder.toUriString(), HttpMethod.GET, null, Object.class);
    }
}
