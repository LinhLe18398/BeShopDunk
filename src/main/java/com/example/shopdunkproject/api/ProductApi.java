package com.example.shopdunkproject.api;

import com.example.shopdunkproject.model.Product;
import com.example.shopdunkproject.model.ProductDTO;
import com.example.shopdunkproject.repository.IAttributeRepository;
import com.example.shopdunkproject.repository.ICategoryRepository;
import com.example.shopdunkproject.repository.IProductAttributeRepository;
import com.example.shopdunkproject.repository.IProductRepository;
import com.example.shopdunkproject.service.IAttributeService;
import com.example.shopdunkproject.service.ICategoryService;
import com.example.shopdunkproject.service.IProductService;
import com.example.shopdunkproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(value = "*")
public class ProductApi {

    @Autowired
    private IProductService iProductService;
    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private IProductAttributeRepository iProductAttributeRepository;
    @Autowired
    private ICategoryRepository iCategoryRepository;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private IAttributeRepository iAttributeRepository;
    @Autowired
    private IAttributeService iAttributeService;
    @Autowired
    private IProductAttributeRepository productAttributeRepository;
    @Autowired
    private ProductService productService;
//
//    @GetMapping
//    public ResponseEntity<Page<Product>> list(@PageableDefault(3) Pageable pageable) {
//        return new ResponseEntity<>(iProductService.findAll(pageable), HttpStatus.OK);
//    }

    @GetMapping("/showAll")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = iProductRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/showProductByCategory/{id}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable long id){
        List<Product> products = iProductRepository.findProductsByCategory_Id(id);
        return ResponseEntity.ok(products);
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = iProductRepository.save(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product productDetails) {
        Optional<Product> existingProduct = iProductRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setImage(productDetails.getImage());
            // Update other fields as necessary
            iProductRepository.save(product);
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/findListProductByCategory")
    public ResponseEntity<Iterable<ProductDTO>> findListProductByCategory() {
        Iterable<ProductDTO> productDTOList = productService.findListProductByCategory();
        return ResponseEntity.ok(productDTOList);
    }


}
