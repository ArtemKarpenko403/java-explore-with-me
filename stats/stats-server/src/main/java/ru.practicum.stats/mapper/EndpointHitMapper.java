package ru.practicum.stats.mapper;

import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.model.Hit;

/**
 * Утилитарный класс для преобразования между DTO и сущностью EndpointHit.
 * <p>
 * Предоставляет методы для двустороннего преобразования данных
 * между объектами передачи данных (DTO) и объектами доменной модели.
 */
public final class EndpointHitMapper {
    /**
     * Преобразует DTO запроса в сущность для сохранения в базе данных.
     *
     * @param endpointHitDto DTO с информацией о запросе к эндпоинту
     * @return сущность EndpointHit для сохранения в БД
     * @throws NullPointerException если endpointHitDto равен null
     */
    public static Hit toEndpointHit(EndpointHitDto endpointHitDto) {
        return Hit.builder()
                .app(endpointHitDto.getApp())
                .ip(endpointHitDto.getIp())
                .timestamp(endpointHitDto.getTimestamp())
                .uri(endpointHitDto.getUri())
                .build();
    }

    /**
     * Преобразует DTO запроса в сущность для сохранения в базе данных.
     *
     * @param endpointHitDto DTO с информацией о запросе к эндпоинту
     * @return сущность EndpointHit для сохранения в БД
     * @throws NullPointerException если endpointHitDto равен null
     */
    public static EndpointHitDto toEndpointHitDto(Hit endpointHitDto) {
        return new EndpointHitDto(
                endpointHitDto.getId(),
                endpointHitDto.getApp(),
                endpointHitDto.getUri(),
                endpointHitDto.getIp(),
                endpointHitDto.getTimestamp()
        );
    }
}
