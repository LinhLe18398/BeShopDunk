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

    @PostMapping("/addToCart/{productId}/{name}")
    public ResponseEntity<String> addProductToCart(@PathVariable String name, @PathVariable long productId, @RequestParam int quantity) {
        Optional<Product> productOptional = iProductRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Optional<User> userOptional = userRepository.findByUserName(name);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Optional<Cart> existingCartItemOptional = iCartRepository.findByProductIdAndUserId(productId, user.getId());
                if (existingCartItemOptional.isPresent()) {
                    Cart existingCartItem = existingCartItemOptional.get();
                    existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                    iCartRepository.save(existingCartItem);
                    return ResponseEntity.ok("Số lượng đã được cập nhật trong giỏ hàng.");
                } else {
                    Cart cartItem = new Cart(product, user, quantity);
                    iCartRepository.save(cartItem);
                    return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng thành công.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không tìm thấy người dùng.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm.");
        }
    }

    @GetMapping("/cart/{username}")
    public ResponseEntity<?> getCartByUser(@PathVariable String username) {
        Optional<User> userOptional = userRepository.findByUserName(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Cart> cartItems = iCartRepository.findByUserId(user.getId());
            return ResponseEntity.ok(cartItems);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng.");
        }
    }
    @GetMapping("/findProductsByCategory/{categoryId}")
    public List<Product> findProductsByCategory(@PathVariable Long categoryId) {
        return productService.findProductsByCategory(categoryId);
    }
    @DeleteMapping("/removeFromCart/{id}")
    public ResponseEntity<?> removeFromCart(@PathVariable long id) {
        Optional<Cart> cartItemOptional = iCartRepository.findById(id);
        if (cartItemOptional.isPresent()) {
            iCartRepository.delete(cartItemOptional.get());
            return ResponseEntity.ok("Sản phẩm đã được xóa khỏi giỏ hàng thành công.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateCart/{id}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable long id, @RequestParam int quantity) {
        Optional<Cart> cartItemOptional = iCartRepository.findById(id);
        if (cartItemOptional.isPresent()) {
            Cart cartItem = cartItemOptional.get();
            cartItem.setQuantity(quantity);
            iCartRepository.save(cartItem);
            return ResponseEntity.ok("Số lượng đã được cập nhật trong giỏ hàng.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy mặt hàng trong giỏ hàng.");
        }
    }

}