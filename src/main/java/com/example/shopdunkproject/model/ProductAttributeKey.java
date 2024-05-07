package com.example.shopdunkproject.model;

import jakarta.persistence.Column;

import java.io.Serializable;

public class ProductAttributeKey implements Serializable {
    @Column(name = "product_id")
    Long productId;

    @Column(name = "attribute_id")
    Long attributeId;

}
