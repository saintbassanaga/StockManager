package tech.saintbassanaga.stockmanager.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.stockmanager.models.Category}
 */
public record UpdateCategoryDto(@NotNull String designation, String description,
                                UUID parentCategoryId) implements Serializable {
}