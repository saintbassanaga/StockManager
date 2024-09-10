package tech.saintbassanaga.stockmanager.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.stockmanager.models.Product}
 */
public record CreateProductDto(@NotNull String name, @NotNull String price, @NotNull String description,
                               UUID categoryId) implements Serializable {
}