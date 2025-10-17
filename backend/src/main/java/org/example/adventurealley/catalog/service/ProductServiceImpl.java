package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.ProductDTO;
import org.example.adventurealley.catalog.dto.ProductMapper;
import org.example.adventurealley.catalog.model.Product;
import org.example.adventurealley.catalog.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepo.findAll();
        List<ProductDTO> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(ProductMapper.toDto(product));
        }
        return productDtos;
    }

    @Override
    public ProductDTO findById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()){
            throw new RuntimeException("Varen blev ikke fundet.");
        }
        return ProductMapper.toDto(product.get());
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = ProductMapper.toEntity(productDTO);
        product.setId(null);
        return ProductMapper.toDto(productRepo.save(product));
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Optional<Product> existingProduct = productRepo.findById(id);
        if (existingProduct.isPresent()){
            Product product = ProductMapper.toEntity(productDTO);
            Product updatedProduct = existingProduct.get();
            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setAmount(product.getAmount());
            return ProductMapper.toDto(productRepo.save(updatedProduct));
        }

        throw new RuntimeException("Varen blev ikke fundet");
    }

    @Override
    public void deleteById(Long id){
        if (productRepo.existsById(id)){
            productRepo.deleteById(id);
        } else {
            throw new RuntimeException("Varen blev ikke fundet.");
        }
    }
}
