package com.example.shopdunkproject.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
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
//    @EqualsAndHashCode.Exclude
    List<ProductAttribute> productAttributes;
}
