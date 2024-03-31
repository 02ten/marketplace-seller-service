package com.seller.sellerservice.repository;

import com.seller.sellerservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameAndUserId(String name, Long id);
    boolean existsByIdAndUserId(Long id, Long userId);
}
