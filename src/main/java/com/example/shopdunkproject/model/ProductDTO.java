package com.example.shopdunkproject.model;

import jakarta.persistence.Entity;

import java.util.List;


public class ProductDTO {
    private Category category;
    private List<Product> products;

    public ProductDTO() {
    }

    public ProductDTO(long id, Category category, List<Product> products) {
        this.category = category;
        this.products = products;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
