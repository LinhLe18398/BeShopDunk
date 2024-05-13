package com.example.shopdunkproject.api;

import com.example.shopdunkproject.model.Product;
import com.example.shopdunkproject.repository.IProductAttributeRepository;
import com.example.shopdunkproject.repository.IProductRepository;
import com.example.shopdunkproject.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductApi {

    @Autowired
    private IProductService service;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private IProductAttributeRepository productAttributeRepository;

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(@PageableDefault(size = 2) Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);
        List<Product> products = productsPage.getContent();
        return ResponseEntity.ok().body(products);
    }


}
