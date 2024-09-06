package tech.saintbassanaga.stockmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String price;
    @NotNull
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}