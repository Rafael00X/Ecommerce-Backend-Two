package com.website.backendtwo.controller;

import com.website.backendtwo.entity.Cart;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.entity.embeddable.Product;
import com.website.backendtwo.service.CartService;
import com.website.backendtwo.service.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private FetchService fetchService;
    @Autowired
    private CartService cartService;
    @GetMapping("/test/products")
    public List<Product> getProducts() {
        List<Integer> ids = Arrays.asList(1, 2, 3, 4, 5);
        return fetchService.getProductsById(ids);
    }

    @PostMapping("/test/add-cart")
    public Cart addCart(@RequestBody User user) {
        return cartService.addCartOfUser(user);
    }
}
