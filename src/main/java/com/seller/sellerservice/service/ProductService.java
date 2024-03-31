package com.seller.sellerservice.service;

import com.seller.sellerservice.dto.ProductDTO;
import com.seller.sellerservice.entity.Category;
import com.seller.sellerservice.entity.Product;
import com.seller.sellerservice.repository.CategoryRepository;
import com.seller.sellerservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public Product createProduct(Long userId, ProductDTO productDTO) {
        log.info("Creating new product");
        if (productRepository.existsByNameAndUserId(productDTO.getName(), userId)) {
            log.error("Cannot create product with existing name");
            throw new IllegalArgumentException("У вас уже есть товар с таким названием");
        }
        Optional<Category> category = categoryService.getCategoryById(productDTO.getCategoryId());
        if(category.isEmpty()){
            log.error("Category does not exist");
            throw new IllegalArgumentException("Такой категории нет");
        }
        Category category1 = category.get();
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setUserId(userId);
        product.setCategory(category1);
        log.info("Product created");
        productRepository.save(product);
        return product;
    }
    public void deleteProduct(Long userId, Long productId){
        log.info("Deleting product");
        if(!productRepository.existsByIdAndUserId(productId, userId)){
            log.error("No such product with that productId and userId");
            throw new IllegalArgumentException("У вас нет такого товара");
        }
        log.info("Deletion successful");
        productRepository.deleteById(productId);

    }
    public List<Product> getAllProducts(Long userId){
        log.info("Getting all products by user id");
        return productRepository.findProductsByUserId(userId);
    }
}
