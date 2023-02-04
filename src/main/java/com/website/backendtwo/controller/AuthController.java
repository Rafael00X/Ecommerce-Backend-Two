package com.website.backendtwo.controller;

import com.website.backendtwo.entity.User;
import com.website.backendtwo.exception.FailedRequestException;
import com.website.backendtwo.exception.NotAuthorizedException;
import com.website.backendtwo.service.CartService;
import com.website.backendtwo.service.UserService;
import com.website.backendtwo.utility.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
        if (newUser == null) throw new FailedRequestException("Failed to register user");
        cartService.addCartOfUser(newUser);
        newUser.setToken(JwtHelper.encode(newUser));
        return newUser;
    }

    @PostMapping("/login")
    public User loginUser(@RequestBody User user) {
        User existingUser = userService.loginUser(user);
        if (existingUser != null) existingUser.setToken(JwtHelper.encode(existingUser));
        return existingUser;
    }

    @PostMapping("/validate")
    public ResponseEntity<Void> validateUser(@RequestHeader(name = "Authorization") String jwtToken, @RequestBody User user) {
        if (!JwtHelper.validate(jwtToken, user.getUserId()))
            throw new NotAuthorizedException();
        return ResponseEntity.ok().build();
    }
}
