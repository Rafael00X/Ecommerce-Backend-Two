package com.website.backendtwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.website.backendtwo.entity.Cart;
import com.website.backendtwo.entity.CartItem;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.exception.InvalidRequestBodyException;
import com.website.backendtwo.service.CartItemService;
import com.website.backendtwo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @PostMapping("/cart")
    public Cart getCart(@RequestBody User user) {
        return cartService.getCartByUser(user);
    }

    // TODO - replace cartItem with productId
    @PostMapping("/cart/add-item")
    public void addItemToCart(@RequestBody ObjectNode objectNode) {
        ObjectMapper mapper = new ObjectMapper();
        User user;
        CartItem cartItem;
        try {
            cartItem = mapper.treeToValue(objectNode.get("item"), CartItem.class);
            user = mapper.treeToValue(objectNode.get("user"), User.class);
        } catch (Exception e) {
            throw new InvalidRequestBodyException();
        }
        Cart cart = cartService.getCartByUser(user);
        cartItem.setCart(cart);
        cartItemService.addCartItem(cartItem);
    }


}
