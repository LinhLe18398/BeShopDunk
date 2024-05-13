package com.example.shopdunkproject.controller;

import com.example.shopdunkproject.model.Category;
import com.example.shopdunkproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService iCategoryService;

    public CategoryController(CategoryService categoryService) {
        this.iCategoryService = categoryService;
    }

    @GetMapping
    public ModelAndView showCategories(Model model) {
        ModelAndView modelAndView = new ModelAndView("/category");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping
    public String createCategory(@ModelAttribute("category") Category category) {
        iCategoryService.save(category);
    return "redirect:/categories";
    }
}