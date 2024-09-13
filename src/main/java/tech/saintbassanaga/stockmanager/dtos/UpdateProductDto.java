package tech.saintbassanaga.stockmanager.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.stockmanager.models.Product}
 */
public record UpdateProductDto(LocalDateTime updateAt, UUID updatedBy, @NotNull String name, @NotNull String price,
                               @NotNull String description) implements Serializable {
}