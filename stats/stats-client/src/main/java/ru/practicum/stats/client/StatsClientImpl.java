package ru.practicum.stats.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
@Service
public class StatsClientImpl implements StatsClient {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final RestTemplate rest;

    @Autowired
    public StatsClientImpl(RestTemplate rest) {
        this.rest = rest;
    }

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
    public void addHit(EndpointHitDto endpointHitDto) {
        log.debug("Отправка информации о посещении: {}", endpointHitDto);
        try {
            rest.postForObject("/hit", endpointHitDto, Void.class);
            log.info("Информация о посещении успешно отправлена: {} {}",
                    endpointHitDto.getApp(), endpointHitDto.getUri());
        } catch (Exception e) {
            log.error("Ошибка при отправке информации о посещении: {}", endpointHitDto, e);
            throw e;
        }
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
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {
        log.debug("Запрос статистики: start={}, end={}, uris={}, unique={}", start, end, uris, unique);

        try {
            Map<String, Object> uriVariables = Map.of(
                    "start", start.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    "end", end.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                    "uris", uris != null ? uris : List.of(),
                    "unique", unique
            );

            ViewStatsDto[] response = rest.getForObject("/stats", ViewStatsDto[].class, uriVariables);

            List<ViewStatsDto> result = response != null ? Arrays.asList(response) : List.of();
            log.info("Получено {} записей статистики", result.size());
            return result;

        } catch (Exception e) {
            log.error("Ошибка при получении статистики: start={}, end={}, uris={}, unique={}",
                    start, end, uris, unique, e);
            throw e;
        }
    }

}
