package tech.saintbassanaga.stockmanager.services.impls;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import tech.saintbassanaga.stockmanager.configs.exception.ErrorCode;
import tech.saintbassanaga.stockmanager.configs.exception.ErrorStatus;
import tech.saintbassanaga.stockmanager.configs.exception.ProductNotFound;
import tech.saintbassanaga.stockmanager.dtos.*;
import tech.saintbassanaga.stockmanager.models.Product;
import tech.saintbassanaga.stockmanager.repositories.CategoryRepository;
import tech.saintbassanaga.stockmanager.repositories.ProductRepository;
import tech.saintbassanaga.stockmanager.services.InventoryService;
import tech.saintbassanaga.stockmanager.services.ProductService;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpls implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DtosMappers dtosMappers;
    private final InventoryService inventoryService;

    public ProductServiceImpls(ProductRepository productRepository, CategoryRepository categoryRepository, DtosMappers dtosMappers, InventoryService inventoryService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.dtosMappers = dtosMappers;
        this.inventoryService = inventoryService;
    }

    @Override
    public List<FindProductDto> findAllProductsByName(String name) {
        return productRepository.findDistinctByNameContaining(name)
                .orElseThrow(() -> new ProductNotFound("Not Found Product with Name Containing " + name + " in the Database", ErrorCode.PRODUCT_NOT_FOUND, ErrorStatus.NOT_FOUND_ENTITY))
                .stream()
                .map(dtosMappers::fromEntityToShortProductDto)
                .toList();
    }

    @Override
    public List<FindProductDto> findAllProductsByCategoryId(UUID categoryId) {
        return productRepository.findDistinctByCategory_Id(categoryId)
                .orElseThrow(() -> new ProductNotFound("Not Found Product with Category Id: " + categoryId + " in the Database", ErrorCode.PRODUCT_NOT_FOUND, ErrorStatus.NOT_FOUND_ENTITY))
                .stream()
                .map(dtosMappers::fromEntityToShortProductDto)
                .toList();
    }

    @Override
    public Product addProduct(ProductDto productDto) throws FileNotFoundException {

        Product product = dtosMappers.createProduct(productDto);
        product.setCategory(categoryRepository.getReferenceById(productDto.categoryId()));

        // Create inventory after adding product
        inventoryService.createInventory(List.of(product));
        return productRepository.save(product);
    }

    @Override
    public UpdateProductDto updateProduct(UUID id, ProductDto productDto, @Nullable UUID categoryId) throws FileNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFound("Not Found product with ID " + id + " in the Database", ErrorCode.PRODUCT_NOT_FOUND, ErrorStatus.NOT_FOUND_ENTITY));

        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setDescription(productDto.description());

        if (categoryId != null) {
            product.setCategory(categoryRepository.getReferenceById(categoryId));
        }

        Product updatedProduct = productRepository.save(product);
        // Create inventory after updating product
        inventoryService.createInventory(List.of(updatedProduct));
        return dtosMappers.updateProductDto(updatedProduct);
    }

    @Override
    public String deleteProduct(UUID productId) throws FileNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFound("Product with ID " + productId + " Not Found", ErrorCode.PRODUCT_NOT_FOUND, ErrorStatus.NOT_FOUND_ENTITY));

        productRepository.deleteById(productId);
        // Create inventory after deleting product
        inventoryService.createFullInventory();
        return "Product Deleted Successfully";
    }

    @Override
    public String deleteMultipleProducts(List<UUID> uuids) throws FileNotFoundException {
        List<Product> products = productRepository.findAllById(uuids);
        productRepository.deleteAll(products);
        // Create inventory after deleting products
        inventoryService.createFullInventory();
        return "Products Deleted Successfully";
    }
}
