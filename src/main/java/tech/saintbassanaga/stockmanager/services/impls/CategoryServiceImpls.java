package tech.saintbassanaga.stockmanager.services.impls;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import tech.saintbassanaga.stockmanager.configs.exception.CategoryNotFound;
import tech.saintbassanaga.stockmanager.configs.exception.ErrorCode;
import tech.saintbassanaga.stockmanager.configs.exception.ErrorStatus;
import tech.saintbassanaga.stockmanager.dtos.CategoryDto;
import tech.saintbassanaga.stockmanager.dtos.DtosMappers;
import tech.saintbassanaga.stockmanager.dtos.FindCategoryDto;
import tech.saintbassanaga.stockmanager.dtos.UpdateCategoryDto;
import tech.saintbassanaga.stockmanager.models.Category;
import tech.saintbassanaga.stockmanager.repositories.CategoryRepository;
import tech.saintbassanaga.stockmanager.services.CategoryService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * MIT License
 *
 * Copyright (c) 2024 saintbassanaga
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


@Service
public class CategoryServiceImpls implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final DtosMappers dtosMappers;

    public CategoryServiceImpls(CategoryRepository categoryRepository, DtosMappers dtosMappers) {
        this.categoryRepository = categoryRepository;
        this.dtosMappers = dtosMappers;
    }

    @Override
    public List<FindCategoryDto> findAllCategoriesByName(String name) {
        return categoryRepository.findCategoriesByDesignationContaining(name)
                .orElseThrow(() -> new CategoryNotFound("Not Found category containing " + name,
                        ErrorCode.CATEGORY_NOT_FOUND,
                        ErrorStatus.NOT_FOUND_ENTITY))
                .stream()
                .map(dtosMappers::fromEntityToShortCategoryDto)
                .toList();
    }

    @Override
    public List<FindCategoryDto> findAllNonEmptyCategories() {
        return categoryRepository.findCategoriesByProductsNotEmpty()
                .orElseThrow(() -> new CategoryNotFound("No non-empty categories found",
                        ErrorCode.CATEGORY_NOT_FOUND,
                        ErrorStatus.NOT_FOUND_ENTITY))
                .stream()
                .map(dtosMappers::fromEntityToShortCategoryDto)
                .toList();
    }

    @Override
    public FindCategoryDto addCategory(CategoryDto categoryDto) {
        Category category = dtosMappers.createCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return dtosMappers.fromEntityToShortCategoryDto(savedCategory);
    }

    @Override
    public UpdateCategoryDto updateCategory(UUID id, CategoryDto categoryDto, @Nullable UUID parentId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("Category with ID " + id + " not found",
                        ErrorCode.CATEGORY_NOT_FOUND,
                        ErrorStatus.NOT_FOUND_ENTITY));

        category.setDescription(categoryDto.description());
        category.setDesignation(categoryDto.designation());

        if (parentId != null) {
            Category parentCategory = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new CategoryNotFound("Parent category with ID " + parentId + " not found",
                            ErrorCode.CATEGORY_NOT_FOUND,
                            ErrorStatus.NOT_FOUND_ENTITY));
            category.setParentCategory(parentCategory);
        }

        Category updatedCategory = categoryRepository.save(category);
        return dtosMappers.fromCategoryToDto(updatedCategory);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFound("Category with ID " + categoryId + " not found",
                    ErrorCode.CATEGORY_NOT_FOUND,
                    ErrorStatus.NOT_FOUND_ENTITY);
        }
        categoryRepository.deleteById(categoryId);
    }
}
