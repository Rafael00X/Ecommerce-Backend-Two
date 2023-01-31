package com.website.backendtwo.service;

import com.website.backendtwo.entity.CartItem;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository repository;

    public CartItem getCartItemById(Integer cartItemId) {
        return repository.findById(cartItemId).orElse(null);
    }

    public CartItem addCartItem(CartItem cartItem) {
        return repository.save(cartItem);
    }

    public void updateCartItem(CartItem cartItem) {
        repository.save(cartItem);
    }

    public void removeCartItemById(Integer cartItemId) {
        repository.deleteById(cartItemId);
    }

    public void removeCartItemsById(List<Integer> cartItemIds) {
        repository.deleteAllById(cartItemIds);
    }

}
