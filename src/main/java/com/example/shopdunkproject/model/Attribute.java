package com.example.shopdunkproject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private long id;
    private String name;
    @OneToMany(mappedBy = "attribute")
    Set<ProductAttribute> productAttributes;


}
