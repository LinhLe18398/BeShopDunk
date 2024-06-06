package com.example.shopdunkproject.api;

import com.example.shopdunkproject.model.Cart;
import com.example.shopdunkproject.model.Product;
import com.example.shopdunkproject.model.ProductDTO;
import com.example.shopdunkproject.model.User;
import com.example.shopdunkproject.repository.ICartRepository;
import com.example.shopdunkproject.repository.IProductRepository;
import com.example.shopdunkproject.repository.UserRepository;

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
    private IProductRepository iProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ICartRepository iCartRepository;

    @Autowired
    private ProductService productService;



    @GetMapping("/findListProductByCategory")
    public List<ProductDTO> findListProductByCategory() {
        return productService.getProductsGroupedByCategory();
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

    @PostMapping("/addToCart/{productId}")
    public ResponseEntity<String> addProductToCart(@PathVariable long productId, @RequestParam int quantity) {
        Optional<Product> productOptional = iProductRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            // Assume user is logged in and identified by userId
            long userId = 1L; // Replace with the actual user ID
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();

                // Check if the product already exists in the cart for the user
                Optional<Cart> existingCartItemOptional = iCartRepository.findByProductIdAndUserId(productId, userId);
                if (existingCartItemOptional.isPresent()) {
                    // If the product already exists in the cart, update the quantity
                    Cart existingCartItem = existingCartItemOptional.get();
                    existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                    iCartRepository.save(existingCartItem);
                    return ResponseEntity.ok("Quantity updated in cart.");
                } else {
                    // Otherwise, create a new cart entry
                    Cart cartItem = new Cart(product, user, quantity); // Set the product, user, and quantity
                    iCartRepository.save(cartItem);
                    return ResponseEntity.ok("Product added to cart successfully.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
    }



    @GetMapping("/cart")
    public ResponseEntity<List<Cart>> getCartItems() {
        List<Cart> cartItems = iCartRepository.findAll();
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }



}