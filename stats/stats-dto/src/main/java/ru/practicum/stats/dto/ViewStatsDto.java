package ru.practicum.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для передачи статистики по посещениям.
 * <p>
 * Содержит агрегированную информацию о количестве просмотров
 * для конкретного URI определенного сервиса.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatsDto {

    /**
     * Название сервиса.
     * Например: "ewm-main-service".
     */
    private String app;

    /**
     * URI сервиса.
     * Например: "/events/1".
     */
    private String uri;

    /**
     * Количество просмотров.
     * Может включать повторные обращения или только уникальные,
     * в зависимости от параметров запроса.
     */
    private Long hits;
}
