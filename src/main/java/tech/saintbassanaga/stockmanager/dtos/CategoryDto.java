package tech.saintbassanaga.stockmanager.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.stockmanager.models.Category}
 */
public record CategoryDto(UUID id, @NotNull String designation,
                          @NotNull(message = "Description can not be null") String description, UUID parentCategoryId) implements Serializable {
}