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
import com.website.backendtwo.utility.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/cart")
    public ResponseEntity<Cart> getCart(@RequestHeader(name = "Authorization") String token) {
        User user = JwtHelper.decode(token);
        Cart cart = cartService.getCartByUser(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/cart/add-item")
    public ResponseEntity<Void> addItemToCart(@RequestHeader(name = "Authorization") String token, @RequestBody Integer productId) {
        User user = JwtHelper.decode(token);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(new Product());
        cartItem.getProduct().setProductId(productId);
        cartItem.setQuantity(1);
        Cart cart = cartService.getCartByUser(user);
        cartItem.setCart(cart);
        cartItemService.addCartItem(cartItem);
        return ResponseEntity.ok(null);
    }

    // TODO - cartItemService.removeCartItem() not getting flushed before cartService.getCartByUser()
    @DeleteMapping("/cart/remove-item")
    public ResponseEntity<Cart> removeItemFromCart(@RequestHeader(name = "Authorization") String token, @RequestBody Integer cartItemId) {
        User user = JwtHelper.decode(token);
        cartItemService.removeCartItemById(cartItemId);
        Cart cart = cartService.getCartByUser(user);
        // TODO - find alternative
        for (int i = 0; i < cart.getCartItems().size(); i++) {
            CartItem cartItem = cart.getCartItems().get(i);
            if (cartItem.getCartItemId().intValue() == cartItemId.intValue()) {
                CartItem deletedItem = cart.getCartItems().remove(i);
                cart.setTotalAmount(cart.getTotalAmount() - deletedItem.calculateAmount());
                break;
            }
        }
        return ResponseEntity.ok(cart);
    }

    // TODO - cartItemService.updateCartItem() not getting flushed before cartService.getCartByUser()
    @PatchMapping("/cart/update-item")
    public ResponseEntity<Cart> updateItemInCart(@RequestHeader(name = "Authorization") String token, @RequestBody CartItem cartItem) {
        User user = JwtHelper.decode(token);
        CartItem savedCartItem = cartItemService.getCartItemById(cartItem.getCartItemId());
        if (savedCartItem == null) throw new RuntimeException("Could not find item in cart");
        savedCartItem.setQuantity(cartItem.getQuantity());
        cartItemService.updateCartItem(savedCartItem);
        Cart cart = cartService.getCartByUser(user);
        // TODO - find alternative
        int totalAmount = 0;
        for (int i = 0; i < cart.getCartItems().size(); i++) {
            CartItem item = cart.getCartItems().get(i);
            if (item.getCartItemId().intValue() == cartItem.getCartItemId().intValue()) {
                cart.getCartItems().get(i).setQuantity(cartItem.getQuantity());
            }
            totalAmount += cart.getCartItems().get(i).calculateAmount();
        }
        cart.setTotalAmount(totalAmount);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/cart/get-item")
    public ResponseEntity<CartItem> getItemFromCart(@RequestHeader(name = "Authorization") String token, @RequestBody Integer productId) {
        User user = JwtHelper.decode(token);
        Cart cart = cartService.getCartByUser(user);
        CartItem item = null;
        for (CartItem cartItem: cart.getCartItems()) {
            if (cartItem.getProduct().getProductId().intValue() == productId.intValue()) {
                item = cartItem;
                break;
            }
        }
        return ResponseEntity.ok(item);
    }
}
