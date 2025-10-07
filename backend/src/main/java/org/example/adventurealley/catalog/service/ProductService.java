package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.model.Product;
import org.example.adventurealley.catalog.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isEmpty()){
            throw new RuntimeException("Varen blev ikke fundet.");
        }
        return product.get();
    }

    public Product createProduct(Product product){
        product.setId(null);
        return productRepo.save(product);
    }

    public Product updateProduct(Long id, Product product){
        Product existingProduct = findById(id);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setAmount(product.getAmount());
        existingProduct.setDescription(product.getDescription());

        return productRepo.save(existingProduct);
    }

    public void deleteById(Long id){
        if (productRepo.existsById(id)){
            productRepo.deleteById(id);
        } else {
            throw new RuntimeException("Varen blev ikke fundet.");
        }
    }
}
