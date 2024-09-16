package tech.saintbassanaga.stockmanager.controllers;

/*
 *
 * Created by saintbassanaga {saintbassanaga}
 * In the Project StockManager at Mon - 9/16/24
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import tech.saintbassanaga.stockmanager.dtos.CategoryDto;
import tech.saintbassanaga.stockmanager.dtos.FindCategoryDto;
import tech.saintbassanaga.stockmanager.dtos.UpdateCategoryDto;
import tech.saintbassanaga.stockmanager.services.CategoryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "API for managing categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/search")
    @Operation(summary = "Find all categories by name")
    @ApiResponse(responseCode = "200", description = "Found categories")
    public ResponseEntity<List<FindCategoryDto>> findAllCategoriesByName(@RequestParam("name") String name) {
        List<FindCategoryDto> categories = categoryService.findAllCategoriesByName(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/non-empty")
    @Operation(summary = "Find all non-empty categories")
    @ApiResponse(responseCode = "200", description = "Found non-empty categories")
    public ResponseEntity<List<FindCategoryDto>> findAllNonEmptyCategories() {
        List<FindCategoryDto> categories = categoryService.findAllNonEmptyCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    @Operation(summary = "Add a new category")
    @ApiResponse(responseCode = "201", description = "Category created")
    public ResponseEntity<FindCategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        FindCategoryDto createdCategory = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(201).body(createdCategory);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category")
    @ApiResponse(responseCode = "200", description = "Category updated")
    public ResponseEntity<UpdateCategoryDto> updateCategory(
            @PathVariable UUID id,
            @RequestBody CategoryDto categoryDto,
            @RequestParam(value = "parentId", required = false) UUID parentId) {
        UpdateCategoryDto updatedCategory = categoryService.updateCategory(id, categoryDto, parentId);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category by ID")
    @ApiResponse(responseCode = "200", description = "Category deleted")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
