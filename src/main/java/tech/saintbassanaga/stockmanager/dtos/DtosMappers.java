package tech.saintbassanaga.stockmanager.dtos;

import tech.saintbassanaga.stockmanager.models.Category;
import tech.saintbassanaga.stockmanager.models.Product;

import java.util.stream.Collectors;

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
public class DtosMappers {

    /**
     *
     * @param category data provider
     * @return built category
     */
    public FullCategoryDto fromEntityToCategoryDto(Category category){
        return new FullCategoryDto(category.getId(), category.getDesignation(), category.getDescription(),
                category.getSubCategories()
                        .stream()
                        .map(this::fromEntityToShortCategoryDto)
                        .collect(Collectors.toList()),
                category.getProducts()
                .stream()
                .map(this::fromEntityToProductDto)
                .collect(Collectors.toList())
        );
    }

    /**
     * @Function fromEntityToShortCategoryDto build news Dto
     * @param category data provider
     * @return full category to be displayed
     */

    public FindCategoryDto fromEntityToShortCategoryDto(Category category){
        return new FindCategoryDto(category.getDesignation(), category.getDescription(),
                category.getProducts()
                        .stream()
                        .map(this::fromEntityToProductDto)
                        .collect(Collectors.toList())
        );
    }

    /**
     * @Function fromEntityToProductDto() create formated Product for display needs.
     * @param product Product that provide data to a DTO
     * @return built Product
     */

   public  ProductDto fromEntityToProductDto(Product product){
        return new ProductDto(
                product.getCreateAt(),product.getUpdateAt(),product.getCreatedBy(),
                product.getUpdatedBy() ,product.getId(),product.getName(),
                product.getPrice(), product.getDescription(),
                product.getCategory().getDesignation());
    }

    /**
     *
     * @param productDto dtos that provide data for category to be build
     * @return built Product
     */
    public Product createProduct(CreateProductDto productDto){
        Product product = new Product();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        return product;
    }

    /**
     * @param categoryDto dtos that provide data for category to be build
     * @return built Category
     */
    public Category createCategory(CategoryDto categoryDto){
        Category category = new Category();
        category.setDesignation(categoryDto.designation());
        category.setDescription(categoryDto.description());
        return category;
    }
}
