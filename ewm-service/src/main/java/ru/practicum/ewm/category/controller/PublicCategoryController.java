package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import java.util.List;

/**
 * Публичный контроллер для получения информации о категориях.
 * Предоставляет доступ к чтению данных категорий.
 */
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryService service;

    /**
     * Получает список категорий с пагинацией.
     *
     * @param from начальный индекс (по умолчанию 0)
     * @param size количество элементов на странице (по умолчанию 10)
     * @return список DTO категорий
     */
    @GetMapping
    public List<CategoryDto> getAll(
            @RequestParam(defaultValue = "0", required = false) Integer from,
            @RequestParam(defaultValue = "10", required = false) Integer size) {
        return service.getAll(from, size);
    }

    /**
     * Получает категорию по идентификатору.
     *
     * @param catId ID категории
     * @return DTO категории
     */
    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable("catId") Long catId) {
        return service.getById(catId);
    }
}