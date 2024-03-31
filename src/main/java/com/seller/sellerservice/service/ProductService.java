package com.seller.sellerservice.service;

import com.seller.sellerservice.dto.ProductDTO;
import com.seller.sellerservice.entity.Category;
import com.seller.sellerservice.entity.Product;
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

    public Product createProduct(Long userId, ProductDTO productDTO) throws IllegalArgumentException{
        log.info("Creating new product");
        Category category = checkingNameProductAndExistingCategory(userId, productDTO);
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setUserId(userId);
        product.setCategory(category);
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
    public Product getProductById(Long userId, Long productId){
        log.info("Getting product by id");
        checkingExistingByIdAndUserId(productId, userId);
        log.info("Successful getting product by id");
        return productRepository.findProductByIdAndUserId(productId, userId);
    }
    public Product updateProduct(Long userId, Long id, ProductDTO updatedProduct) throws IllegalArgumentException{
        log.info("Updating product");
        checkingExistingByIdAndUserId(id, userId);
        Category category = checkingNameProductAndExistingCategory(userId, updatedProduct);
        Product product = new Product(
                id,
                updatedProduct.getName(),
                updatedProduct.getPrice(),
                updatedProduct.getDescription(),
                userId,
                category
        );
        productRepository.save(product);
        return product;
    }
    private Category checkingNameProductAndExistingCategory(Long userId, ProductDTO productDTO){
        if (productRepository.existsByNameAndUserId(productDTO.getName(), userId)) {
            log.error("Cannot create product with existing name");
            throw new IllegalArgumentException("У вас уже есть товар с таким названием");
        }
        Optional<Category> category = categoryService.getCategoryById(productDTO.getCategoryId());
        if(category.isEmpty()){
            log.error("Category does not exist");
            throw new IllegalArgumentException("Такой категории нет");
        }
        return category.get();
    }
    private void checkingExistingByIdAndUserId(Long id, Long userId){
        if(!productRepository.existsByIdAndUserId(id, userId)){
            log.error("Not found product with such product id");
            throw new IllegalArgumentException("У вас нет такого товара");
        }
    }
}
