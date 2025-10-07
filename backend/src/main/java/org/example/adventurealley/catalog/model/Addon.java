package org.example.adventurealley.catalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.example.adventurealley.common.baseClasses.BaseEntity;

@Entity
public class Addon extends BaseEntity {
    @ManyToOne
    private Booking booking;

    @ManyToOne
    private Product product;

    private int quantity;

    public Addon() {}

    public Addon(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
