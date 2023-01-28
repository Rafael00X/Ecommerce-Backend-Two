package com.website.backendtwo.service;

import com.website.backendtwo.entity.Cart;
import com.website.backendtwo.entity.CartItem;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository repository;

    public Cart getCart(Integer userId) {
        Cart cart = repository.findByUserUserId(userId).orElse(addCart(userId));
        int total = 0;
        for (CartItem cartItem: cart.getCartItems())
            total += cartItem.getSellingPrice() * cartItem.getQuantity();
        cart.setTotalAmount(total);
        return cart;
    }

    public Cart addCart(Integer userId) {
        Cart cart = new Cart();
        cart.setUser(new User());
        cart.getUser().setUserId(userId);
        return repository.save(cart);
    }
}
