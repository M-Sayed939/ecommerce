package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart getCartForUser(String username){
        User user= userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }
    @Transactional
    public Cart addItemToCart(String username, Long productId, int quantity){
        User user= userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + productId));
        if(product.getStockQuantity() < quantity){
            throw new RuntimeException("Insufficient stock for product " + product.getName());
        }
        Cart cart= getCartForUser(username);
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
        if(existingItem.isPresent()){
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);

        }else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setCart(cart);
            cart.getItems().add(cartItemRepository.save(newCartItem));
        }
        return cart;
    }
}
