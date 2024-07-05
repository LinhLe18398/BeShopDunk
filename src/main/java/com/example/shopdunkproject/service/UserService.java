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
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public String registerUser(User user) {
        System.out.println("Registering user: " + user);
        Optional<User> existingUserName = userRepository.findByUserName(user.getUserName());
        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());

        if (existingUserName.isPresent()) {
            return "username đã tồn tại";
        } else if (existingEmail.isPresent()) {
            return "Email đã ồn tại";
        } else {
            userRepository.save(user);
            return "Đăng ký tài khoản thành công";
        }
    }

    public User loginUser(String userName, String password) {
        Optional<User> user = userRepository.findByUserNameAndPassword(userName, password);
        return user.orElse(null);
    }
}
