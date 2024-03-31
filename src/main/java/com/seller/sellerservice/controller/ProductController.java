package com.seller.sellerservice.controller;

import com.seller.sellerservice.dto.ProductDTO;
import com.seller.sellerservice.entity.Product;
import com.seller.sellerservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("/{id}")
    public Product addNewProduct(@PathVariable Long id, ProductDTO productDTO){
        Product product = productService.createProduct(id, productDTO);
        return product;
    }
}
