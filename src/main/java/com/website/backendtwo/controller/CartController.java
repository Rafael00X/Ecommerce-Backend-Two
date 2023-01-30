package com.website.backendtwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.website.backendtwo.entity.Cart;
import com.website.backendtwo.entity.CartItem;
import com.website.backendtwo.entity.User;
import com.website.backendtwo.entity.embeddable.Product;
import com.website.backendtwo.exception.InvalidRequestBodyException;
import com.website.backendtwo.service.CartItemService;
import com.website.backendtwo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/cart/add-item")
    public ResponseEntity<Void> addItemToCart(@RequestBody ObjectNode objectNode) {
        ObjectMapper mapper = new ObjectMapper();
        User user;
        Integer productId;
        try {
            user = mapper.treeToValue(objectNode.get("user"), User.class);
            productId = mapper.treeToValue(objectNode.get("productId"), Integer.class);
        } catch (Exception e) {
            throw new InvalidRequestBodyException();
        }
        CartItem cartItem = new CartItem();
        cartItem.setProduct(new Product());
        cartItem.getProduct().setProductId(productId);
        cartItem.setQuantity(1);
        Cart cart = cartService.getCartByUser(user);
        cartItem.setCart(cart);
        cartItemService.addCartItem(cartItem);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cart/remove-item")
    public ResponseEntity<Cart> removeItemFromCart(@RequestBody ObjectNode objectNode) {
        ObjectMapper mapper = new ObjectMapper();
        User user;
        Integer cartItemId;
        try {
            user = mapper.treeToValue(objectNode.get("user"), User.class);
            cartItemId = mapper.treeToValue(objectNode.get("cartItemId"), Integer.class);
        } catch (Exception e) {
            throw new InvalidRequestBodyException();
        }
        cartItemService.removeCartItem(cartItemId);
        Cart cart = cartService.getCartByUser(user);
        return ResponseEntity.ok().body(cart);
    }
}
