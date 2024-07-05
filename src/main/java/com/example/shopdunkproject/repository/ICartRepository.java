package com.example.shopdunkproject.repository;

import com.example.shopdunkproject.model.Cart;
import com.example.shopdunkproject.model.Product;
import com.example.shopdunkproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
//    Optional<Cart> findByUserAndProduct(User user, Product product);
//
//    List<Cart> findByUser(User user);

    Optional<Cart> findByProductIdAndUserId(long productId, long userId);

    List<Cart> findByUserId(long id);

    List<Cart> findByUser(User user);
}
