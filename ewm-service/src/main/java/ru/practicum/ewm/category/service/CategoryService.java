package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.dto.UpdateCategoryDto;
import ru.practicum.ewm.exceptions.ConflictException;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.util.List;

/**
 * Интерфейс сервиса для работы с категориями.
 * Определяет контракт операций CRUD для категорий событий.
 */
public interface CategoryService {
    /**
     * Получает список категорий с пагинацией.
     *
     * @param from начальная позиция (по умолчанию 0)
     * @param size количество элементов (по умолчанию 10)
     * @return список DTO категорий
     */
    List<CategoryDto> getAll(Integer from, Integer size);

    /**
     * Получает категорию по идентификатору.
     *
     * @param catId ID категории
     * @return DTO категории
     * @throws NotFoundException если категория не найдена
     */
    CategoryDto getById(Long catId);

    /**
     * Создает новую категорию.
     *
     * @param category DTO с данными новой категории
     * @return созданная DTO категории
     */
    CategoryDto create(NewCategoryDto category);

    /**
     * Обновляет существующую категорию.
     *
     * @param category DTO с обновленными данными
     * @param catId    ID обновляемой категории
     * @return обновленная DTO категории
     * @throws NotFoundException если категория не найдена
     */
    CategoryDto update(UpdateCategoryDto category, Long catId);

    /**
     * Удаляет категорию по идентификатору.
     *
     * @param catId ID удаляемой категории
     * @throws NotFoundException если категория не найдена
     * @throws ConflictException если категория содержит события
     */
    void deleteById(Long catId);
}
