package com.example.shopdunkproject.api;

import com.example.shopdunkproject.model.Product;
import com.example.shopdunkproject.model.ProductDTO;
import com.example.shopdunkproject.repository.ICategoryRepository;
import com.example.shopdunkproject.repository.IProductRepository;
import com.example.shopdunkproject.service.ICategoryService;
import com.example.shopdunkproject.service.IProductService;
import com.example.shopdunkproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private ProductService productService;

    @GetMapping("/cart")
    public ResponseEntity<List<Product>> getCartItems() {
        return new ResponseEntity<>(productListCart, HttpStatus.OK);
    }

    @DeleteMapping("/removeFromCart/{id}")
    public ResponseEntity<List<Product>> removeFromCart(@PathVariable long id) {
        productListCart.removeIf(product -> product.getId() == id);
        return new ResponseEntity<>(productListCart, HttpStatus.OK);
    }

    List<Product> productListCart = new ArrayList<>();
//    public List<Product> resetProductListCart(){
//        return productListCart;
//    }
    @PostMapping("/addToCart/{id}")
    public ResponseEntity<List<Product>> addToCart(@PathVariable long id){
       Optional<Product> product = iProductRepository.findById(id);
        productListCart.add(product.get());
        return new ResponseEntity<>(productListCart, HttpStatus.OK);
    }

    @GetMapping("/showAll")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = iProductRepository.findAll();
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

    @GetMapping("getInfoProduct/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        Optional<Product> product = iProductRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
