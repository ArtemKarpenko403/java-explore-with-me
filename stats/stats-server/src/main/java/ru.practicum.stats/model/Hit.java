package ru.practicum.stats.model;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Модель для хранения информации о запросе к эндпоинту.
 * <p>
 * Содержит данные о том, что на определенный URI конкретного сервиса
 * был отправлен HTTP-запрос от пользователя с конкретного IP-адреса.
 */
@Entity
@Data
@Builder
@Table(name = "hit")
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String app;
    @Column(nullable = false)
    private String uri;
    @Column(nullable = false)
    private String ip;
    @Column(nullable = false)
    private LocalDateTime timestamp;
}
