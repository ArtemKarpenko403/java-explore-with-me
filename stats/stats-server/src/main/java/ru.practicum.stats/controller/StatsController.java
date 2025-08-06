package ru.practicum.stats.controller;


import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.exceptions.InvalidDateException;
import ru.practicum.stats.service.StatsService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * REST контроллер для работы со статистикой посещений эндпоинтов.
 * <p>
 * Предоставляет API для:
 * - Сохранения информации о запросах к эндпоинтам ({@link #hit(EndpointHitDto)})
 * - Получения агрегированной статистики по посещениям ({@link #getStats(LocalDateTime, LocalDateTime, List, boolean)})
 *
 * @see StatsService
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@Validated
public class StatsController {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatsService statsService;

    /**
     * Сохраняет информацию о запросе к эндпоинту.
     *
     * @param endpointHitDto данные о запросе к эндпоинту
     * @return ResponseEntity с HTTP статусом 201 (Created)
     * @throws jakarta.validation.ConstraintViolationException если данные запроса не прошли валидацию
     */
    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void hit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        log.info("Попытка добавления запроса: {}", endpointHitDto);
        statsService.addHit(endpointHitDto);
        log.info("Запрос на {} от {} сохранён", endpointHitDto.getUri(), endpointHitDto.getIp());
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
    public ResponseEntity<List<ViewStatsDto>> getStats(@RequestParam @NonNull String start,
                                                       @RequestParam @NonNull String end,
                                                       @RequestParam(required = false, defaultValue = "") List<String> uris,
                                                       @RequestParam(defaultValue = "false") Boolean unique) {
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            String decodedStart = URLDecoder.decode(start, StandardCharsets.UTF_8);
            String decodedEnd = URLDecoder.decode(end, StandardCharsets.UTF_8);
            startDateTime = LocalDateTime.parse(decodedStart, FORMATTER);
            endDateTime = LocalDateTime.parse(decodedEnd, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("неправильный формат даты: " + e.getMessage());
        }
        log.info("Запрошена статистика от {} до {}", start, end);
        List<ViewStatsDto> results = statsService.getStats(startDateTime, endDateTime, uris, unique);
        return ResponseEntity.ok(results);
    }
}
