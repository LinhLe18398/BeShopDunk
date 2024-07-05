package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.Cart;
import com.example.shopdunkproject.model.User;
import com.example.shopdunkproject.repository.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    public CartService(ICartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // Phương thức để xóa các mặt hàng trong giỏ hàng dựa trên user ID
    public void deleteCartItemsByUserId(long userId) {
        cartRepository.deleteById(userId);
    }

    public List<Cart> findByUser(User user) {
        return cartRepository.findByUser(user);
    }
    public void deleteAll(List<Cart> cartItems) {
        cartRepository.deleteAll(cartItems);
    }
}
