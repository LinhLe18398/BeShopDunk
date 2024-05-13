package com.example.shopdunkproject.model;

import jakarta.persistence.Column;

import java.io.Serializable;

public class ProductAttributeKey implements Serializable {
    @Column(name = "product_id")
    long productId;

    @Column(name = "attribute_id")
    long attributeId;

    public ProductAttributeKey() {
    }

    public ProductAttributeKey(long productId, long attributeId) {
        this.productId = productId;
        this.attributeId = attributeId;
    }

}
