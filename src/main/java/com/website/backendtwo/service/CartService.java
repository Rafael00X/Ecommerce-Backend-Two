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

    public Cart getCart(User user) {
        Cart cart = repository.findByUser(user).orElse(addCart(user));
        if (cart.getCartItems() == null) cart.setCartItems(new ArrayList<>());
        int total = 0;
        for (CartItem cartItem: cart.getCartItems())
            total += cartItem.getSellingPrice() * cartItem.getQuantity();
        cart.setTotalAmount(total);
        return cart;
    }

    public Cart addCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return repository.save(cart);
    }
}
