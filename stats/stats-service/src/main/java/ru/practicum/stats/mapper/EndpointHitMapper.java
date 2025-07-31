package ru.practicum.stats.mapper;

import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.model.EndpointHit;

/**
 * Утилитарный класс для преобразования между DTO и сущностью EndpointHit.
 * <p>
 * Предоставляет методы для двустороннего преобразования данных
 * между объектами передачи данных (DTO) и объектами доменной модели.
 */
public final class EndpointHitMapper {
    // Приватный конструктор для utility class
    private EndpointHitMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Преобразует DTO запроса в сущность для сохранения в базе данных.
     *
     * @param endpointHitDto DTO с информацией о запросе к эндпоинту
     * @return сущность EndpointHit для сохранения в БД
     * @throws NullPointerException если endpointHitDto равен null
     */
    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return new EndpointHit(
                endpointHitDto.getId(),
                endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                endpointHitDto.getTimestamp()
        );
    }

    /**
     * Преобразует сущность из базы данных в DTO для передачи клиенту.
     *
     * @param endpointHit сущность запроса из базы данных
     * @return DTO с информацией о запросе к эндпоинту
     * @throws NullPointerException если endpointHit равен null
     */
    public static EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return new EndpointHitDto(
                endpointHit.getId(),
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp()
        );
    }
}
