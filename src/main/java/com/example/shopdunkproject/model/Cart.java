package com.example.shopdunkproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int quantity;

    // Constructors, getters, and setters
    public CartItem() {}

    public CartItem(Product product, User user, int quantity) {
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }

}
