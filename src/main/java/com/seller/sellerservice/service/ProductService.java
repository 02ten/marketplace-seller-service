package com.seller.sellerservice.service;

import com.seller.sellerservice.dto.ProductDTO;
import com.seller.sellerservice.entity.Product;
import com.seller.sellerservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(Long userId, ProductDTO productDTO) {
        log.info("Creating new product");
        if (productRepository.existsByNameAndUserId(productDTO.getName(), userId)) {
            log.error("Cannot create product with existing name");
            throw new IllegalArgumentException("У вас уже есть товар с таким названием");
        }
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setUserId(userId);
        productRepository.save(product);
        return product;
    }
}
