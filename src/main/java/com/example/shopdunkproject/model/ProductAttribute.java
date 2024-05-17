package com.example.shopdunkproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductAttribute {
    @EmbeddedId
    ProductAttributeKey id;
    private String value;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id")
    Attribute attribute;

}
