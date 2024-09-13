package tech.saintbassanaga.stockmanager.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link tech.saintbassanaga.stockmanager.models.Product}
 */
public record CreateProductDto(LocalDateTime createAt, LocalDateTime updateAt, UUID createdBy, UUID updatedBy, UUID id,
                         @NotNull String name, @NotNull @Length(message = "Review your price length", max = 15)
                         String price,

                         @NotNull(message = "Non Nullable Field")
                         @Size(message = "Description must be minimum 25 characters", min = 25, max = 100)
                         String description,

                         @NotNull(message = "Category Cannot be Null")
                         String categoryDesignation
) implements Serializable {
}