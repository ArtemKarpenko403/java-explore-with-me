package ru.practicum.stats.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.service.StatsService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * REST контроллер для работы со статистикой посещений эндпоинтов.
 * <p>
 * Предоставляет API для:
 * - Сохранения информации о запросах к эндпоинтам ({@link #addHit(EndpointHitDto)})
 * - Получения агрегированной статистики по посещениям ({@link #getStats(LocalDateTime, LocalDateTime, List, boolean)})
 *
 * @see StatsService
 */

@Slf4j
@RequiredArgsConstructor
@RestController
public class StatsController {
    private final StatsService statsService;

    /**
     * Сохраняет информацию о запросе к эндпоинту.
     *
     * @param endpointHitDto данные о запросе к эндпоинту
     * @return ResponseEntity с HTTP статусом 201 (Created)
     * @throws jakarta.validation.ConstraintViolationException если данные запроса не прошли валидацию
     */
    @PostMapping("/hit")
    public ResponseEntity<Void> addHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        log.info("Попытка добавления запроса: {}", endpointHitDto);
        statsService.addHit(endpointHitDto);
        log.info("Запрос на {} от {} сохранён", endpointHitDto.getUri(), endpointHitDto.getIp());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Получает статистику по посещениям с заданными параметрами фильтрации.
     * <p>
     * Обратите внимание: значение даты и времени нужно закодировать
     * (например используя {@link java.net.URLEncoder#encode(String, String)})
     *
     * @param start  дата и время начала диапазона (в формате "yyyy-MM-dd HH:mm:ss")
     * @param end    дата и время конца диапазона (в формате "yyyy-MM-dd HH:mm:ss")
     * @param uris   список URI для фильтрации (опциональный параметр)
     * @param unique флаг учета только уникальных посещений по IP (по умолчанию false)
     * @return список статистики по посещениям, отсортированный по количеству просмотров по убыванию
     * @throws jakarta.validation.ConstraintViolationException если параметры даты некорректны
     */
    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") boolean unique) {

        if (uris != null && uris.size() == 1 && "".equals(uris.get(0))) {
            uris = Collections.emptyList();
        }

        log.info("Запрошена статистика от {} до {}", start, end);
        return statsService.getStats(start, end, uris, unique);
    }

}
