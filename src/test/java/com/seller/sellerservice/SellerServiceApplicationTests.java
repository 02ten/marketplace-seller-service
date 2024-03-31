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

import java.util.ArrayList;
import java.util.List;
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
        Assertions.assertTrue(new ReflectionEquals(expectedProduct).matches(actualProduct));
    }

    @Test
    void createProduct_withInvalidName_ThrowsIllegalArgumentException() {
        ProductDTO productDTO = new ProductDTO("invalidName", 299.99, "validDescription", 1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.createProduct(1L, productDTO));
    }

    @Test
    void createProduct_withInvalidCategoryId_ThrowsIllegalArgumentException() {
        ProductDTO productDTO = new ProductDTO("validName", 299.99, "validDescription", 1L);
        Mockito.when(categoryService.getCategoryById(productDTO.getCategoryId())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.createProduct(1L, productDTO));
    }

    @Test
    void deleteProduct_withValidProductIdAndUserId_Checking() {
        Mockito.when(productRepository.existsByIdAndUserId(1L, 1L)).thenReturn(true);
        productService.deleteProduct(1L, 1L);
        Mockito.verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_withInvalidProductIdAndUserId_ThrowsIllegalArgumentException() {
        Mockito.when(productRepository.existsByIdAndUserId(1L, 1L)).thenReturn(false);
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(1L, 1L));
    }

    @Test
    void getProductList_withValidUserId_returnsListProduct() {
        Product product1 = new Product(1L, "validName", 299.99, "validDescription", 1L, new Category());
        Product product2 = new Product(2L, "validName", 299.99, "validDescription", 1L, new Category());
        Product product3 = new Product(3L, "validName", 299.99, "validDescription", 1L, new Category());
        List<Product> expectedList = new ArrayList<>(List.of(product1, product2, product3));
        Mockito.when(productRepository.findProductsByUserId(1L)).thenReturn(new ArrayList<>(List.of(product1, product2, product3)));
        List<Product> actualList = productService.getAllProducts(1L);
        Assertions.assertArrayEquals(expectedList.toArray(), actualList.toArray());
    }

    @Test
    void getProduct_withValidUserIdAndProductId_returnsProduct() {
        Category category = new Category(1, "category1", null);
        Product expectedProduct = new Product(1L, "validName", 299.99, "validDescription", 1L, category);
        Mockito.when(productRepository.existsByIdAndUserId(1L, 1L)).thenReturn(true);
        Mockito.when(productRepository.findProductByIdAndUserId(1L, 1L)).thenReturn(new Product
                (1L, "validName", 299.99, "validDescription", 1L, category));
        Product actualProduct = productService.getProductById(1L,1L);
        Assertions.assertTrue(new ReflectionEquals(expectedProduct).matches(actualProduct));
    }
    @Test
    void getProduct_withInvalidUserIdAndProductId_ThrowsIllegalArgumentException(){
        Mockito.when(productRepository.existsByIdAndUserId(1L,1L)).thenReturn(false);
        Assertions.assertThrows(IllegalArgumentException.class, ()->productService.getProductById(1L,1L));
    }
    @Test
    void updateProduct_withValidName_ReturnsProduct() {
        Category category = new Category(1L, "category", null);
        Product expectedProduct = new Product(1L, "newName", 299.99, "validDescription", 1L, category);
        ProductDTO productDTO = new ProductDTO("newName", 299.99, "validDescription", 1L);
        Mockito.when(productRepository.existsByNameAndUserId(productDTO.getName(), 1L)).thenReturn(false);
        Mockito.when(categoryService.getCategoryById(productDTO.getCategoryId())).thenReturn(Optional.of(category));
        Mockito.when(productRepository.existsByIdAndUserId(1L,1L)).thenReturn(true);
        Product actualProduct = productService.updateProduct(1L,1L, productDTO);
        System.out.println(actualProduct.toString());
        System.out.println(expectedProduct.toString());
        Assertions.assertTrue(new ReflectionEquals(expectedProduct).matches(actualProduct));
    }

    @Test
    void updateProduct_withInvalidName_ThrowsIllegalArgumentException() {
        ProductDTO productDTO = new ProductDTO("invalidName", 299.99, "validDescription", 1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.createProduct(1L, productDTO));
    }

    @Test
    void updateProduct_withInvalidCategoryId_ThrowsIllegalArgumentException() {
        ProductDTO productDTO = new ProductDTO("validName", 299.99, "validDescription", 1L);
        Mockito.when(categoryService.getCategoryById(productDTO.getCategoryId())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.createProduct(1L, productDTO));
    }
}
