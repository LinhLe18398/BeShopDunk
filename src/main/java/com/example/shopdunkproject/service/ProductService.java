package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.Category;
import com.example.shopdunkproject.model.Product;
import com.example.shopdunkproject.model.ProductDTO;
import com.example.shopdunkproject.repository.ICategoryRepository;
import com.example.shopdunkproject.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Override
    public void delete(long id) {
        iProductRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(long id) {
        return iProductRepository.findById(id);
    }

    public void addToCart(Product product){

    }

    @Override
    public Iterable<ProductDTO> findListProductByCategory() {
        List<ProductDTO> productDTOList =  new ArrayList<>();
        List<Category> categoryList = iCategoryRepository.findAll();
        List<Product> productList = iProductRepository.findAll();

        for (int i = 0; i < categoryList.size() ; i++) {
            Category category = categoryList.get(i);
            List<Product> products = new ArrayList<>();
            for (int j = 0; j < productList.size() ; j++) {
                Product product = productList.get(j);
                if (product.getCategory().getName().equals(category.getName())){
                    products.add(productList.get(j));
                }
            }
            ProductDTO productDTO = new ProductDTO(i,category,products);
            productDTOList.add(productDTO);
        }
        return productDTOList;
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