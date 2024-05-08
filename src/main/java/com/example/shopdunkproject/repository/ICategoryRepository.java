package com.example.shopdunkproject.repository;

import com.example.shopdunkproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category,Long> {
}
