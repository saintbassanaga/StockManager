package tech.saintbassanaga.stockmanager.controllers;

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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import tech.saintbassanaga.stockmanager.dtos.FindProductDto;
import tech.saintbassanaga.stockmanager.dtos.ProductDto;
import tech.saintbassanaga.stockmanager.dtos.UpdateProductDto;
import tech.saintbassanaga.stockmanager.models.Product;
import tech.saintbassanaga.stockmanager.services.ProductService;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "API for managing products")
public class ProductController{

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    @Operation(summary = "Find all products by name")
    @ApiResponse(responseCode = "200", description = "Found products")
    public ResponseEntity<List<FindProductDto>> findAllProductsByName(
            @RequestParam("name") String name) {
        List<FindProductDto> products = productService.findAllProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Find all products by category ID")
    @ApiResponse(responseCode = "200", description = "Found products")
    public ResponseEntity<List<FindProductDto>> findAllProductsByCategoryId(
            @PathVariable UUID categoryId) {
        List<FindProductDto> products = productService.findAllProductsByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @Operation(summary = "Add a new product")
    @ApiResponse(responseCode = "201", description = "Product created")
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto) throws FileNotFoundException {
        Product createdProduct = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    @ApiResponse(responseCode = "200", description = "Product updated")
    public ResponseEntity<UpdateProductDto> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductDto productDto,
            @RequestParam(value = "categoryId", required = false) UUID categoryId) throws FileNotFoundException {
        UpdateProductDto updatedProduct = productService.updateProduct(id, productDto, categoryId);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by ID")
    @ApiResponse(responseCode = "200", description = "Product deleted")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) throws FileNotFoundException {
        String response = productService.deleteProduct(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "Delete multiple products by IDs")
    @ApiResponse(responseCode = "200", description = "Products deleted")
    public ResponseEntity<String> deleteMultipleProducts(@RequestParam List<UUID> ids) throws FileNotFoundException {
        String response = productService.deleteMultipleProducts(ids);
        return ResponseEntity.ok(response);
    }
}
