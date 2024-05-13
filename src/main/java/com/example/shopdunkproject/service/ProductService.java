package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.Product;
import com.example.shopdunkproject.repository.IProductAttributeRepository;
import com.example.shopdunkproject.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductAttributeRepository repository;
    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public void delete(long id) {
        iProductRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(long id) {
        return iProductRepository.findById(id);
    }


    @Override
    public Page<Product> findAll(Pageable pageable) {
        return iProductRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return iProductRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return iProductRepository.save(product);
    }

    @Override
    public void remove(long id) {
        iProductRepository.deleteById(id);

    }
    @Override
    public List<Product> findByNameContainingAndPriceGreaterThanEqual(String name, BigDecimal price) {
        return null;
    }
}
