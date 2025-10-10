package org.example.adventurealley.catalog.dto;

public record AddonDTO(Long id, org.example.adventurealley.catalog.model.Booking booking, org.example.adventurealley.catalog.model.Product product, int quantity) {}
