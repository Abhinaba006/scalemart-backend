package com.abhinaba.scalemart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 1, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Seller ID is required")
    private Long sellerId;
}