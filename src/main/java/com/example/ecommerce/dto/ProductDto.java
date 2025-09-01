package com.example.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    @NotBlank(message = "Product name is required")
    private String name;
    private String description;
    @Positive(message = "Price must be positive")
    private double price;
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private int stockQuantity;
}
