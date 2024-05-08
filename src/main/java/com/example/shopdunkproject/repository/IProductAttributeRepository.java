package com.example.shopdunkproject.repository;

import com.example.shopdunkproject.model.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductAttributeRepository extends JpaRepository<ProductAttribute,Long> {
}
