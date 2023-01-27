package com.website.backendtwo.service;

import com.website.backendtwo.entity.CartItem;
import com.website.backendtwo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CartItemService {
    @Autowired
    private CartItemRepository repository;

    public void addCartItem(CartItem cartItem) {
        repository.save(cartItem);
    }

    public void updateCartItem(CartItem cartItem) {
        repository.save(cartItem);
    }

    public void removeCartItem(Integer id) {
        repository.deleteById(id);
    }

}
