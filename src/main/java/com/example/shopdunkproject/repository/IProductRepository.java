package com.example.shopdunkproject.repository;

import com.example.shopdunkproject.model.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    List<Product> findProductsByCategory_Id(Long id);
}
