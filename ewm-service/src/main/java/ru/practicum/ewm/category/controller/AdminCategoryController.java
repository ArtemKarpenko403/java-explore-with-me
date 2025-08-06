package ru.practicum.ewm.category.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.dto.UpdateCategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

/**
 * Контроллер для административных операций с категориями.
 * Позволяет создавать, обновлять и удалять категории.
 */
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService service;

    /**
     * Создает новую категорию.
     *
     * @param category DTO с данными для создания
     * @return созданная категория
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody @Valid NewCategoryDto category) {
        return service.create(category);
    }

    /**
     * Обновляет существующую категорию.
     *
     * @param category DTO с обновленными данными
     * @param catId    ID категории для обновления
     * @return обновленная категория
     */
    @PatchMapping("/{catId}")
    public CategoryDto update(@RequestBody @Valid UpdateCategoryDto category,
                              @PathVariable("catId") Long catId) {
        return service.update(category, catId);
    }

    /**
     * Удаляет категорию по ID.
     *
     * @param catId ID категории для удаления
     */
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("catId") Long catId) {
        service.deleteById(catId);
    }
}
