package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.Product;
import com.example.shopdunkproject.model.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface IProductService extends IGenerateService<Product> {


    void delete(long id);

    Optional<Product> findById(long id);

    Iterable<ProductDTO> findListProductByCategory();
}
