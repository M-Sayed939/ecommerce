package com.example.ecommerce.service;

import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<Order> getOrderForUser(String username){
        User user= userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
        return orderRepository.findByUserId(user.getId());
    }

    @Transactional
    public Order createOrderFromCart(String username){
        User user= userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username " + username));
        var cart= cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found for user " + username));
        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Cart is empty for user " + username);
        }


        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        order.setOrderItems(new ArrayList<>());
        for (CartItem cartItem : cart.getItems()
             ) {
            Product product = cartItem.getProduct();
            if(product.getStockQuantity() < cartItem.getQuantity()){
                throw new RuntimeException("Insufficient stock for product " + product.getName());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPricePerUnit(product.getPrice());
            order.getOrderItems().add(orderItem);

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }
        Order savedOrder = orderRepository.save(order);
        // Clear cart
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
        cartRepository.save(cart);
        return savedOrder;
    }
}
