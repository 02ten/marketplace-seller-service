package com.seller.sellerservice;

import com.seller.sellerservice.dto.ProductDTO;
import com.seller.sellerservice.entity.Category;
import com.seller.sellerservice.entity.Product;
import com.seller.sellerservice.repository.ProductRepository;
import com.seller.sellerservice.service.CategoryService;
import com.seller.sellerservice.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class SellerServiceApplicationTests {
    @Mock
    CategoryService categoryService;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductService productService;

    @Test
    void createProduct_withValidName_ReturnsProduct() {
        Category category = new Category(1L, "category", null);
        Product expectedProduct = new Product(null, "validName", 299.99, "validDescription", 1L, category);
        ProductDTO productDTO = new ProductDTO("validName", 299.99, "validDescription", 1L);
        Mockito.when(productRepository.existsByNameAndUserId(productDTO.getName(), 1L)).thenReturn(false);
        Mockito.when(categoryService.getCategoryById(productDTO.getCategoryId())).thenReturn(Optional.of(category));
        Product actualProduct = productService.createProduct(1L, productDTO);
        System.out.println(actualProduct.toString());

        Assertions.assertTrue(new ReflectionEquals(expectedProduct).matches(actualProduct));
    }
}
