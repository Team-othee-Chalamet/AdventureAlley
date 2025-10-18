package org.example.adventurealley.common.config;

import org.example.adventurealley.catalog.model.Product;
import org.example.adventurealley.catalog.repository.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductInitData implements CommandLineRunner {
    ProductRepo productRepo;

    public ProductInitData(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Product product1 = new Product("Coca Cola", 20, "33cl");
        Product product2 = new Product("Fanta Orange", 20, "33cl");
        Product product3 = new Product("Sprite", 20, "33cl");
        Product product4 = new Product("Red Bull", 30, "25cl");
        Product product5 = new Product("Kildevand", 15, "50cl");

        Product product6 = new Product("Chips – Salt", 25, "40g");
        Product product7 = new Product("Popcorn", 20, "30g");
        Product product8 = new Product("M&M’s", 25, "45g");
        Product product9 = new Product("Haribo Matador Mix", 25, "80g");
        Product product10 = new Product("Snickers", 15, "50g");
        Product product11 = new Product("Twix", 15, "50g");

        productRepo.saveAll(List.of(product1, product2, product3,
                product4, product5, product6, product7, product8,
                product9, product10, product11));
    }
}
