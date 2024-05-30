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
    private long id;
    private String name;
    private int quantity;
    private double price;
    private String image;
    private String description;
    private int discount;


    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;


    @OneToOne
    public ProductDetail productDetail;

}