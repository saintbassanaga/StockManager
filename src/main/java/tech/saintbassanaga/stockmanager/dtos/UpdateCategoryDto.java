package tech.saintbassanaga.stockmanager.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link tech.saintbassanaga.stockmanager.models.Category}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record UpdateCategoryDto(@NotNull String designation, @NotNull String description) implements Serializable {
}