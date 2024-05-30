package com.example.shopdunkproject.repository;

import com.example.shopdunkproject.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductDetailRepository extends JpaRepository<ProductDetail,Long> {
}
