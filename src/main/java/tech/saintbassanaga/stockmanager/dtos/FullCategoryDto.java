package tech.saintbassanaga.stockmanager.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.stockmanager.models.Category}
 */
public record FullCategoryDto(UUID id, @NotNull String designation, String description,
                              List<FindCategoryDto> subCategories, List<ProductDto> products) implements Serializable {
}