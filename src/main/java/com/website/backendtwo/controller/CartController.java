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

    // TODO - cartItemService.removeCartItem() not getting flushed before cartService.getCartByUser()
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
        for (int i = 0; i < cart.getCartItems().size(); i++) {
            CartItem cartItem = cart.getCartItems().get(i);
            if (cartItem.getId().intValue() == cartItemId.intValue()) {
                CartItem deletedItem = cart.getCartItems().remove(i);
                cart.setTotalAmount(cart.getTotalAmount() - deletedItem.calculateAmount());
                break;
            }
        }
        return ResponseEntity.ok().body(cart);
    }

//    @PatchMapping("/cart/update-item")
//    public ResponseEntity<Cart> updateItemInCart(@RequestBody ObjectNode objectNode) {
//        ObjectMapper mapper = new ObjectMapper();
//        User user;
//        Integer cartItemId, quantity;
//        try {
//            user = mapper.treeToValue(objectNode.get("user"), User.class);
//            cartItemId = mapper.treeToValue(objectNode.get("cartItemId"), Integer.class);
//            quantity = mapper.treeToValue(objectNode.get("quantity"), Integer.class);
//        } catch (Exception e) {
//            throw new InvalidRequestBodyException();
//        }
//        cartItemService.removeCartItem(cartItemId);
//        Cart cart = cartService.getCartByUser(user);
//        for (int i = 0; i < cart.getCartItems().size(); i++) {
//            CartItem cartItem = cart.getCartItems().get(i);
//            if (cartItem.getId().intValue() == cartItemId.intValue()) {
//                CartItem deletedItem = cart.getCartItems().remove(i);
//                cart.setTotalAmount(cart.getTotalAmount() - deletedItem.calculateAmount());
//                break;
//            }
//        }
//        return ResponseEntity.ok().body(cart);
//    }
}
