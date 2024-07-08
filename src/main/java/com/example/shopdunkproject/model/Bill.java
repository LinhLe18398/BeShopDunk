package com.example.shopdunkproject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id")
    private List<BillItem> billItems;

    private Date orderDate;
    private double totalAmount;

    public Bill() {
        this.orderDate = new Date();
    }

    public String getFormattedTotalAmount() {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(totalAmount);
    }
}
