package com.example.shopdunkproject.service;

import com.example.shopdunkproject.model.User;
import com.example.shopdunkproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String registerUser(User user) {
        System.out.println("Registering user: " + user);
        Optional<User> existingUserName = userRepository.findByUserName(user.getUserName());
        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());

        if (existingUserName.isPresent()) {
            return "Username already exists";
        } else if (existingEmail.isPresent()) {
            return "Email already exists";
        } else {
            userRepository.save(user);
            return "User registered successfully";
        }
    }

    public User loginUser(String userName, String password) {
        Optional<User> user = userRepository.findByUserNameAndPassword(userName, password);
        return user.orElse(null);
    }
}
