package ru.practicum.stats.client;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

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
    ResponseEntity<Void> addHit(@NotBlank String uri, @NotBlank String ip);
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
    ResponseEntity<Object> getStats(@NotNull String start, @NotNull String end,
                                    @Nullable List<String> uris,
                                    @Nullable Boolean unique);
}