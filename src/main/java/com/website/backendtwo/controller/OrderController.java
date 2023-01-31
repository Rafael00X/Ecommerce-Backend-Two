package com.website.backendtwo.controller;

import com.website.backendtwo.entity.Cart;
import com.website.backendtwo.entity.CartItem;
import com.website.backendtwo.entity.OrderItem;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.service.CartItemService;
import com.website.backendtwo.service.CartService;
import com.website.backendtwo.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class OrderController {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @PostMapping("/orders")
    public List<OrderItem> getOrders(@RequestBody User user) {
        return orderItemService.getOrdersOfUser(user);
    }

    @PostMapping("/orders/add-orders")
    public ResponseEntity<Void> addOrders(@RequestBody User user) {
        Cart cart = cartService.getCartByUser(user);
        List<OrderItem> orderItems = new ArrayList<>();
        List<Integer> cartItemIds = new ArrayList<>();
        for (CartItem cartItem: cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUser(user);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductId(cartItem.getProduct().getProductId());
            orderItem.setProductName(cartItem.getProduct().getProductName());
            orderItem.setTotalPrice(cartItem.calculateAmount());
            orderItem.setImageUrl(cartItem.getProduct().getImageUrl());
            orderItems.add(orderItem);
            cartItemIds.add(cartItem.getCartItemId());
        }
        orderItemService.addOrdersOfUser(orderItems);
        cartItemService.removeCartItemsById(cartItemIds);
        return ResponseEntity.ok().build();
    }
}
