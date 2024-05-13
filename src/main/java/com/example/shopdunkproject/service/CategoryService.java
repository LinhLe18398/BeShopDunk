package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.Category;
import com.example.shopdunkproject.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;


    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void remove(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findByNameContainingAndPriceGreaterThanEqual(String name, BigDecimal price) {
        return List.of();
    }
}
