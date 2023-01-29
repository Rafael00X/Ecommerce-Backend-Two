package com.website.backendtwo.controller;

import com.website.backendtwo.entity.embeddable.Product;
import com.website.backendtwo.service.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private FetchService fetchService;
    @GetMapping("/test/products")
    public List<Product> getProducts() {
        List<Integer> ids = Arrays.asList(1, 2, 3, 4, 5);
        return fetchService.getProductsById(ids);
    }
}
