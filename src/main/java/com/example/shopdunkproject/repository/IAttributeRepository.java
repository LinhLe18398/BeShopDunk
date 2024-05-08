package com.example.shopdunkproject.repository;

import com.example.shopdunkproject.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAttributeRepository extends JpaRepository<Attribute,Long> {
}
