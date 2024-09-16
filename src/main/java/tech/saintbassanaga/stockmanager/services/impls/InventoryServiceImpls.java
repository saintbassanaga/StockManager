package tech.saintbassanaga.stockmanager.services.impls;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.saintbassanaga.stockmanager.configs.exception.PdfGenerationException;
import tech.saintbassanaga.stockmanager.dtos.CreateProductDto;
import tech.saintbassanaga.stockmanager.dtos.DtosMappers;
import tech.saintbassanaga.stockmanager.dtos.ProductDto;
import tech.saintbassanaga.stockmanager.models.Inventory;
import tech.saintbassanaga.stockmanager.models.Product;
import tech.saintbassanaga.stockmanager.repositories.InventoryRepository;
import tech.saintbassanaga.stockmanager.repositories.ProductRepository;
import tech.saintbassanaga.stockmanager.services.InventoryService;
import tech.saintbassanaga.stockmanager.services.PdfGeneratorService;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * Created by saintbassanaga {saintbassanaga}
 * In the Project StockManager at Mon - 9/16/24
 */

@Service
public class InventoryServiceImpls implements InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpls.class);

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final PdfGeneratorService pdfGeneratorService;

    public InventoryServiceImpls(InventoryRepository inventoryRepository, ProductRepository productRepository, PdfGeneratorService pdfGeneratorService) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.pdfGeneratorService = pdfGeneratorService;
    }

   @Transactional
    public Inventory createInventory(List<Product> products) {
       // Ensure proxies are initialized
       products.forEach(product -> {
           if (product.getCategory() != null) {
               product.getCategory().getDesignation(); // Initialize category
           }
       });

       // Group products by category
       Map<String, List<Product>> productsByCategory = products.stream()
               .collect(Collectors.groupingBy(p -> p.getCategory().getDesignation()));

        // Create inventory
        Inventory inventory = new Inventory();
        inventory.setProduct(products);
        // Generate PDF
        try {
            String pdfFilePath = pdfGeneratorService.generateInventoryPdf(inventory);
            inventory.setPdfFilePath(pdfFilePath);
        } catch (FileNotFoundException e) {
            logger.error("Error generating PDF for inventory", e);
            throw new PdfGenerationException("Error generating PDF for inventory", e);
        }
        // Save inventory
       return inventoryRepository.save(inventory);
    }

    @Transactional
    public Inventory createFullInventory() {
        List<Product> allProducts = productRepository.findAll();
        return createInventory(allProducts);
    }
}


