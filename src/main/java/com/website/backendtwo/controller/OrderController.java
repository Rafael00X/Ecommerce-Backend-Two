package com.website.backendtwo.controller;

import com.website.backendtwo.entity.OrderItem;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderItemService orderItemService;
    @PostMapping("/orders")
    public List<OrderItem> getOrders(@RequestBody User user) {
        return orderItemService.getOrdersOfUser(user);
    }

    @PostMapping("/orders/add-orders")
    public void addOrders(@RequestBody List<OrderItem> orders) {
        // TODO - getCart and then add orders
    }
}
