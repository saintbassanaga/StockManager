package tech.saintbassanaga.stockmanager.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.saintbassanaga.stockmanager.configs.exception.ErrorCode;
import tech.saintbassanaga.stockmanager.configs.exception.ErrorStatus;
import tech.saintbassanaga.stockmanager.configs.exception.ResourceNotFound;
import tech.saintbassanaga.stockmanager.models.Inventory;
import tech.saintbassanaga.stockmanager.models.Product;
import tech.saintbassanaga.stockmanager.repositories.InventoryRepository;
import tech.saintbassanaga.stockmanager.services.InventoryService;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by saintbassanaga {saintbassanaga}
 * In the Project StockManager at Mon - 9/16/24
 */
@RestController
@AllArgsConstructor
@Tag(name = "Inventory", description = "API for managing Inventories")
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;

    @PostMapping("/create")
    public ResponseEntity<Inventory> createInventory(@RequestBody List<Product> product) throws FileNotFoundException {
        Inventory inventory = inventoryService.createInventory(product);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/last-pdf")
    public ResponseEntity<Resource> getLastInventoryPdf() {
        Inventory lastInventory = inventoryRepository.findTopByOrderByCreateAtDesc()
                .orElseThrow(() -> new ResourceNotFound("No inventories found", ErrorCode.RESOURCE_NOT_FOUND, ErrorStatus.FILE_ERROR));

        FileSystemResource file = new FileSystemResource(lastInventory.getPdfFilePath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }

}

