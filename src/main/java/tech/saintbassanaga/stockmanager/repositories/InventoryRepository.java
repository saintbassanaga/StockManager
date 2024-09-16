package tech.saintbassanaga.stockmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.saintbassanaga.stockmanager.models.Inventory;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    Optional<Inventory> findTopByOrderByCreateAtDesc();
}