package com.website.backendtwo.service;

import com.website.backendtwo.entity.Cart;
import com.website.backendtwo.entity.CartItem;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {
    @Autowired
    private CartRepository repository;

    // TODO - fetch Product details from other RestAPI
    public Cart getCartByUser(User user) {
        Cart cart = repository.findByUser(user).orElse(addCartOfUser(user));
        if (cart.getCartItems() == null) cart.setCartItems(new ArrayList<>());
        int total = 0;
        for (CartItem cartItem: cart.getCartItems())
            total += cartItem.getProduct().getSellingPrice() * cartItem.getQuantity();
        cart.setTotalAmount(total);
        return cart;
    }

    // TODO - fetch Product details from other RestAPI
    public Cart getCartById(Integer cartId) {
        Cart cart = repository.findById(cartId).orElse(null);
        if (cart == null) return null;
        if (cart.getCartItems() == null) cart.setCartItems(new ArrayList<>());
        int total = 0;
        for (CartItem cartItem: cart.getCartItems())
            total += cartItem.getProduct().getSellingPrice() * cartItem.getQuantity();
        cart.setTotalAmount(total);
        return cart;
    }

    private Cart addCartOfUser(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return repository.save(cart);
    }
}
