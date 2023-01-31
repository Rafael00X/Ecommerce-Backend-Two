package com.website.backendtwo.controller;

import com.website.backendtwo.entity.User;
import com.website.backendtwo.service.CartService;
import com.website.backendtwo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        User newUser = userService.registerUser(user);
        if (newUser != null) {
            newUser.setToken("user-token");
            cartService.addCartOfUser(newUser);
        }
        return newUser;
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        User existingUser = userService.loginUser(user);
        if (existingUser != null) existingUser.setToken("user-token");
        return existingUser;
    }
}
