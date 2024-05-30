package com.example.shopdunkproject.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDTO {
    private long id;
    private Category category;
    private List<Product> products;

    public ProductDTO() {
    }

    public ProductDTO(long id, Category category, List<Product> products) {
        this.id = id;
        this.category = category;
        this.products = products;
    }
}