package com.example.shopdunkproject.request;

import com.example.shopdunkproject.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private boolean success;
    private String message;
    private User user;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

}
