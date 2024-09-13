package tech.saintbassanaga.stockmanager.services.impls;

import org.springframework.lang.Nullable;
import tech.saintbassanaga.stockmanager.configs.exception.ErrorCode;
import tech.saintbassanaga.stockmanager.configs.exception.ErrorStatus;
import tech.saintbassanaga.stockmanager.configs.exception.ProductNotFound;
import tech.saintbassanaga.stockmanager.dtos.*;
import tech.saintbassanaga.stockmanager.models.Product;
import tech.saintbassanaga.stockmanager.repositories.CategoryRepository;
import tech.saintbassanaga.stockmanager.repositories.ProductRepository;
import tech.saintbassanaga.stockmanager.services.ProductService;
import tech.saintbassanaga.stockmanager.dtos.UpdateProductDto;

import java.util.List;
import java.util.UUID;

/*
 * MIT License
 *
 * Copyright (c) 2024 saintbassanaga
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class ProductServiceImpls implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DtosMappers dtosMappers;

    public ProductServiceImpls(ProductRepository productRepository, CategoryRepository categoryRepository, DtosMappers dtosMappers) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.dtosMappers = dtosMappers;
    }

    @Override
    public List<FindProductDto> findAllProductsByName(String name) {
        return productRepository.findDistinctByNameContaining(name).orElseThrow(
                ()-> new ProductNotFound("Not Found Product with Name Containing" + name + "in the DataBase",
                        ErrorCode.PRODUCT_NOT_FOUND,
                        ErrorStatus.NOT_FOUND_ENTITY)
        )
                .stream()
                .map(DtosMappers::fromEntityToShortProductDto)
                .toList();
    }

    @Override
    public List<FindProductDto> findAllProductsByCategoryId(UUID categoryId) {
        return productRepository.findDistinctByCategory_Id(categoryId).orElseThrow(
                        ()-> new ProductNotFound("Not Found Product with Category Id : " + categoryId + "in the DataBase",
                                ErrorCode.PRODUCT_NOT_FOUND,
                                ErrorStatus.NOT_FOUND_ENTITY)
                )
                .stream()
                .map(DtosMappers::fromEntityToShortProductDto)
                .toList();
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        return productRepository.save(dtosMappers.createProduct(productDto)) ;
    }

    @Override
    public UpdateProductDto updateProduct(UUID id, ProductDto product, @Nullable UUID categoryId) {
        Product  prod = productRepository
                .findById(id)
                .orElseThrow(
                        ()-> new ProductNotFound("Not Found product with"+ id + "In the dataBase", ErrorCode.PRODUCT_NOT_FOUND, ErrorStatus.NOT_FOUND_ENTITY)
                );
        prod.setName(product.name());
        prod.setPrice(product.price());
        prod.setDescription(product.description());
        if (categoryId!= null)
            prod.setCategory(categoryRepository.getReferenceById(categoryId));
        return dtosMappers.updateProductDto(productRepository.save(prod));
    }

    @Override
    public String deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFound("Product with Id" + productId + "Not Found", ErrorCode.PRODUCT_NOT_FOUND, ErrorStatus.NOT_FOUND_ENTITY)
        );
        if (product != null)
            productRepository.deleteById(productId);
        return "Product Deleted Successfully";
    }

    @Override
    public String deleteMultipleProducts(List<UUID> uuids) {
        productRepository.deleteAll(productRepository.findAllById(uuids));
        return "";
    }
}
