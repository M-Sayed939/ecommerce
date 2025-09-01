package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AddItemToCartRequest;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        Cart cart = cartService.getCartForUser(userDetails.getUsername());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addItemToCart(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody AddItemToCartRequest request) {
        Cart updatedCart = cartService.addItemToCart(userDetails.getUsername(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }
}
