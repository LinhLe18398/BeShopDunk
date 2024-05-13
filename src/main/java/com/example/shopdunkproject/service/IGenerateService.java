package com.example.shopdunkproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IGenerateService<T> {
    Page<T> findAll(Pageable pageable);

    Optional<T> findById(Long id);

    T save(T t);

    void remove(long id);

    List<T> findByNameContainingAndPriceGreaterThanEqual(String name, BigDecimal price);

}
