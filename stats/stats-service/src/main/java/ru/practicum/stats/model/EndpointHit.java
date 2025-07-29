package ru.practicum.stats.model;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Модель для хранения информации о запросе к эндпоинту.
 * <p>
 * Содержит данные о том, что на определенный URI конкретного сервиса
 * был отправлен HTTP-запрос от пользователя с конкретного IP-адреса.
 */
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hits")
public class EndpointHit {

    /**
     * Идентификатор записи.
     * Генерируется базой данных автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Идентификатор сервиса, для которого записывается информация.
     * Например: "ewm-main-service".
     */
    @Column(name = "app", nullable = false)
    private String app;

    /**
     * URI, для которого был осуществлен запрос.
     * Например: "/events/1".
     */
    @Column(name = "uri", nullable = false)
    private String uri;

    /**
     * IP-адрес пользователя, осуществившего запрос.
     * Например: "192.163.0.1".
     */
    @Column(name = "ip", nullable = false)
    private String ip;

    /**
     * Дата и время, когда был совершен запрос к эндпоинту.
     * Формат: "yyyy-MM-dd HH:mm:ss".
     */
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}
