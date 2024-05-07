package com.example.shopdunkproject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long id;
    private String name;
    private String description;
    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    Set<ProductAttribute> productAttributes;
}
