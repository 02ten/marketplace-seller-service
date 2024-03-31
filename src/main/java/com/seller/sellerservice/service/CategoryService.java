package com.seller.sellerservice.service;

import com.seller.sellerservice.entity.Category;
import com.seller.sellerservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Optional<Category> getCategoryById(Long categoryId){
        return categoryRepository.findById(categoryId);
    }
}
