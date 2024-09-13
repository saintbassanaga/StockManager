package tech.saintbassanaga.stockmanager.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.stockmanager.models.Product}
 */
public record FindProductDto(UUID id, @NotNull String name, @NotNull String price, @NotNull String description,
                             String categoryDesignation) implements Serializable {
}