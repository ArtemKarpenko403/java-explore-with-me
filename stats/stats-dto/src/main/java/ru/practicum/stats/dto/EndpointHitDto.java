package ru.practicum.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO для передачи информации о запросе к эндпоинту.
 * <p>
 * Содержит данные о том, что на определенный URI конкретного сервиса
 * был отправлен HTTP-запрос от пользователя с конкретного IP-адреса.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndpointHitDto {

    /**
     * Идентификатор записи (для обновления существующих записей).
     * При создании новых записей может быть null.
     */
    private Long id;

    /**
     * Идентификатор сервиса, для которого записывается информация.
     * Не должен быть пустым.
     */
    @NotBlank
    private String app;

    /**
     * URI, для которого был осуществлен запрос.
     * Не должен быть пустым.
     */
    @NotBlank
    private String uri;

    /**
     * IP-адрес пользователя, осуществившего запрос.
     * Не должен быть пустым.
     */
    @NotBlank
    private String ip;

    /**
     * Дата и время, когда был совершен запрос к эндпоинту.
     * Формат: "yyyy-MM-dd HH:mm:ss".
     * Не должен быть null.
     */
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}
