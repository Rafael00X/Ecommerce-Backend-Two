package com.website.backendtwo.service;

import com.website.backendtwo.entity.CartItem;
import com.website.backendtwo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository repository;

    public CartItem addCartItem(CartItem cartItem) {
        return repository.save(cartItem);
    }

    public void updateCartItem(CartItem cartItem) {
        repository.save(cartItem);
    }

    public void removeCartItem(Integer id) {
        repository.deleteById(id);
    }

}
