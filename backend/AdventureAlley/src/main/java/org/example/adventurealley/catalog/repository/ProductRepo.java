package org.example.adventurealley.catalog.repository;

import org.example.adventurealley.catalog.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
