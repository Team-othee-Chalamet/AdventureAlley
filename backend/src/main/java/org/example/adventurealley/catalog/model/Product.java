package org.example.adventurealley.catalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.example.adventurealley.common.baseClasses.BaseEntity;

@Entity
public class Product extends BaseEntity {
    private String name;
    private double price;
    private String amount;

    public Product() {}

    public Product(String name, double price, String amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
