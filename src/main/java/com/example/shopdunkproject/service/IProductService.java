package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.Product;

import java.util.Optional;

public interface IProductService extends IGenerateService<Product>{



     void delete(long id);

     Optional<Product> findById(long id);

}
