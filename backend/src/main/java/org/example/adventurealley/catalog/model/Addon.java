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
}
