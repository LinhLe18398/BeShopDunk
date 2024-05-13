package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.ProductAttribute;
import com.example.shopdunkproject.repository.IProductAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductAttributeService implements IProductAttributeService {
    @Autowired
    private IProductAttributeRepository iProductAttributeRepository;

    @Override
    public Page<ProductAttribute> findAll(Pageable pageable) {
        return iProductAttributeRepository.findAll(pageable);
    }

    @Override
    public Optional<ProductAttribute> findById(Long id) {
        return iProductAttributeRepository.findById(id);
    }

    @Override
    public ProductAttribute save(ProductAttribute productAttribute) {
        return iProductAttributeRepository.save(productAttribute);
    }

    @Override
    public void remove(long id) {
        iProductAttributeRepository.deleteById(id);
    }

    @Override
    public List<ProductAttribute> findByNameContainingAndPriceGreaterThanEqual(String name, BigDecimal price) {
        return null;
    }
}
