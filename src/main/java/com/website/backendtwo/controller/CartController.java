package com.website.backendtwo.controller;

import com.website.backendtwo.entity.Cart;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping("/cart")
    public Cart getCart(@RequestBody User user) {
        return cartService.getCart(user);
    }


}
