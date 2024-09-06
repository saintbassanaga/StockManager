package tech.saintbassanaga.stockmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import tech.saintbassanaga.stockmanager.models.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
  Optional<Product> findProductById(UUID uuid);
  Optional<List<Product>> findDistinctByCategory_Id(UUID categoryId);
  Optional<List<Product>> findDistinctByNameContaining(String name);
  void deleteDistinctById(UUID uuid);
}