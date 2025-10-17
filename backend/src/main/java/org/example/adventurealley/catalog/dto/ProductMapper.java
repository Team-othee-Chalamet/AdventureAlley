package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Product;

public class ProductMapper {

    static public ProductDTO toDto (Product product){
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getAmount());
    }

    static public Product toEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        product.setAmount(productDTO.amount());
        return product;
    }
}
