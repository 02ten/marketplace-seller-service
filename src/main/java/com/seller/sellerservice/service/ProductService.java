package com.seller.sellerservice.service;

import com.seller.sellerservice.dto.ProductDTO;
import com.seller.sellerservice.entity.Product;
import com.seller.sellerservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public Product createProduct(Long id, ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setUserId(id);
        productRepository.save(product);
        return product;
    }
}
