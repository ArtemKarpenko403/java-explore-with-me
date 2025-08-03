package ru.practicum.ewm.category.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность категории для событий.
 * Содержит уникальное название категории.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    /**
     * Уникальный идентификатор категории
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название категории (уникальное, обязательное поле)
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;
}