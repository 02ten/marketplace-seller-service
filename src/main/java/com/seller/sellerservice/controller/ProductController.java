package com.seller.sellerservice.controller;

import com.seller.sellerservice.dto.ProductDTO;
import com.seller.sellerservice.entity.Product;
import com.seller.sellerservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("/{userId}")
    public ResponseEntity<Product> addNewProduct(@PathVariable Long userId, ProductDTO productDTO){
        Product product;
        try{
             product = productService.createProduct(userId, productDTO);
        }catch (IllegalArgumentException ex){
            product = null;
        }
        return ResponseEntity.ok(product);
    }

}
