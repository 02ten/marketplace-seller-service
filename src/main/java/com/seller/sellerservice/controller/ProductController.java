package com.seller.sellerservice.controller;

import com.seller.sellerservice.dto.ProductDTO;
import com.seller.sellerservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping("/{userId}")
    public ResponseEntity<String> addNewProduct(@PathVariable Long userId, @RequestBody ProductDTO productDTO){
        try{
             productService.createProduct(userId, productDTO);
             return new ResponseEntity<>("Товар добавлен", HttpStatus.CREATED);
        }catch (IllegalArgumentException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
