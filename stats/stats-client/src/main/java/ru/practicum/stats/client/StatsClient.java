package ru.practicum.stats.client;


import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Клиент для взаимодействия со службой статистики.
 * <p>
 * Предоставляет методы для отправки информации о запросах к эндпоинтам
 * и получения агрегированной статистики по посещениям.
 * <p>
 * Используется другими микросервисами для сбора и анализа статистики посещений.
 */
public interface StatsClient {

    /**
     * Отправляет информацию о запросе к эндпоинту в службу статистики.
     *
     * @param endpointHitDto данные о запросе к эндпоинту
     * @throws org.springframework.web.client.RestClientException при ошибках HTTP запроса
     * @throws jakarta.validation.ConstraintViolationException    если данные запроса некорректны
     */
    void addHit(EndpointHitDto endpointHitDto);

    /**
     * Получает статистику по посещениям с заданными параметрами фильтрации.
     *
     * @param start  дата и время начала диапазона (включительно)
     * @param end    дата и время конца диапазона (включительно)
     * @param uris   список URI для фильтрации (может быть null или пустым)
     * @param unique флаг учета только уникальных посещений по IP
     * @return список статистики по посещениям, отсортированный по количеству просмотров по убыванию
     * @throws org.springframework.web.client.RestClientException при ошибках HTTP запроса
     */
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique);
}