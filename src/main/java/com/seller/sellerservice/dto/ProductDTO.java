package com.seller.sellerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private double price;
    private String description;
    private Long categoryId;
}
