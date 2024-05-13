package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.Attribute;
import com.example.shopdunkproject.repository.IAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AttributeService implements IAttributeService {
    @Autowired
    private IAttributeRepository iAttributeRepository;

    @Override
    public Page<Attribute> findAll(Pageable pageable) {
        return iAttributeRepository.findAll(pageable);
    }

    @Override
    public Optional<Attribute> findById(Long id) {
        return iAttributeRepository.findById(id);
    }

    @Override
    public Attribute save(Attribute attribute) {
        return iAttributeRepository.save(attribute);
    }

    @Override
    public void remove(long id) {
        iAttributeRepository.deleteById(id);
    }

    @Override
    public List<Attribute> findByNameContainingAndPriceGreaterThanEqual(String name, BigDecimal price) {
        return null;
    }
}
