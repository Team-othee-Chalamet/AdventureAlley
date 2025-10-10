package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();

    ProductDTO findById(Long id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteById(Long id);
}
