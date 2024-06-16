package com.example.shopdunkproject.api;

import com.example.shopdunkproject.model.User;
import com.example.shopdunkproject.repository.ICartRepository;
import com.example.shopdunkproject.repository.UserRepository;
import com.example.shopdunkproject.service.CartService;
import com.example.shopdunkproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(value = "*")
public class UserApi {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ICartRepository iCartRepository;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        String userName = loginData.get("userName");
        String password = loginData.get("password");

        User user = userService.loginUser(userName, password);
        if (user != null) {
            return ResponseEntity.ok(Map.of("success", true, "user", user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "sai tên đăng nhập hoặc mật khẩu "));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestBody Map<String, String> logoutData) {
        String userName = logoutData.get("userName");

        Optional<User> user = userRepository.findByUserName(userName);
        if (user != null) {
            // Xóa các mặt hàng trong giỏ hàng của người dùng
            cartService.deleteCartItemsByUserId(user.get().getId());
            // Các logic đăng xuất bổ sung (xóa session, cookies, v.v.) có thể được thêm vào đây

            return ResponseEntity.ok("Đã đăng xuất thành công.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng.");
        }
    }
}
