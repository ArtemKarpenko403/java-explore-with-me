package ru.practicum.stats.service;

import jakarta.validation.ValidationException;
import org.springframework.dao.DataAccessException;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы со статистикой посещений эндпоинтов.
 * <p>
 * Предоставляет функциональность для:
 * - Сохранения информации о запросах к эндпоинтам
 * - Получения статистики по посещениям с различными фильтрами
 */
public interface StatsService {

    /**
     * Сохраняет информацию о том, что к эндпоинту был отправлен запрос.
     *
     * @param endpointHitDto данные о запросе к эндпоинту
     * @throws ValidationException если данные запроса некорректны
     * @throws DataAccessException если произошла ошибка сохранения в БД
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
     * @throws ValidationException если диапазон дат некорректен
     */
    List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
