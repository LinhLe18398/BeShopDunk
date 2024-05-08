package com.example.shopdunkproject.controller;

import com.example.shopdunkproject.repository.IProductAttributeRepository;
import com.example.shopdunkproject.repository.IProductRepository;
import com.example.shopdunkproject.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/products")
public class AdminController {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private IProductAttributeRepository iProductAttributeRepository;

    @GetMapping("/showAll")
    public ModelAndView showAllProduct(@PageableDefault(2) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("homeAdmin");
        modelAndView.addObject("listValue",iProductAttributeRepository.findAll());
        modelAndView.addObject("listProduct", iProductRepository.findAll(pageable));
        return modelAndView;
    }
}
