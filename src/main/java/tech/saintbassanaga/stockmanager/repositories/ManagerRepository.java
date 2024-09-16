package tech.saintbassanaga.stockmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.saintbassanaga.stockmanager.models.Manager;

import java.util.UUID;

public interface ManagerRepository extends JpaRepository<Manager, UUID> {
}