package tech.saintbassanaga.stockmanager.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link tech.saintbassanaga.stockmanager.models.Category}
 */
public record FindCategoryDto(@NotNull String designation, String description,
                               List<ProductDto> products) implements Serializable {
}