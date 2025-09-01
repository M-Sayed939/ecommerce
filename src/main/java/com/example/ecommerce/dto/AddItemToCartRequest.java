package com.example.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartRequest {
    @NotNull(message = "Product ID is required")
    private long productId;
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
