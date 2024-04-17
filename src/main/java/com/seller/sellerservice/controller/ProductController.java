package com.seller.sellerservice.controller;

import com.seller.sellerservice.dto.ProductDTO;
import com.seller.sellerservice.entity.Product;
import com.seller.sellerservice.service.ProductService;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.tag.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/seller/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    private final ProductService productService;
    private final Tracer tracer;

    @PostMapping(value = "/{userId}", produces = {"multipart/form-data"})
    public ResponseEntity<String> addNewProduct(@ModelAttribute ProductDTO productDTO,
                                                @PathVariable Long userId) {
        Span span = tracer.buildSpan("Adding new product").start();
        Tags.HTTP_METHOD.set(span, "POST");
        Tags.HTTP_URL.set(span, "/api/product/" + userId);
        span.finish();
        try {
            productService.createProduct(userId, productDTO);
            return new ResponseEntity<>("Товар добавлен", HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long userId, @PathVariable Long productId) {
        Span span = tracer.buildSpan("Delete product").start();
        Tags.HTTP_METHOD.set(span, "DELETE");
        Tags.HTTP_URL.set(span, "/api/product/" + userId + "/" + productId);
        span.finish();
        try {
            productService.deleteProduct(userId, productId);
            return new ResponseEntity<>("Товар успешно удален", HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable Long userId) {
        Span span = tracer.buildSpan("Get all products").start();
        Tags.HTTP_METHOD.set(span, "GET");
        Tags.HTTP_URL.set(span, "/api/product/" + userId);
        span.finish();
        List<Product> productList = productService.getAllProducts(userId);
        for (Product product : productList)
            System.out.println(product.toString());
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/{userId}/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long userId, @PathVariable Long productId) {
        Span span = tracer.buildSpan("Get product").start();
        Tags.HTTP_METHOD.set(span, "GET");
        Tags.HTTP_URL.set(span, "/api/product/" + userId + "/" + productId);
        span.finish();
        try {
            Product product = productService.getProductById(userId, productId);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//TODO
//    @PutMapping("/{userId}/{productId}")
//    public ResponseEntity<?> updateProduct(@PathVariable Long userId, @PathVariable Long productId,
//                                           @RequestBody ProductDTO updatedProduct) {
//        Span span = tracer.buildSpan("Update product").start();
//        Tags.HTTP_METHOD.set(span, "PUT");
//        Tags.HTTP_URL.set(span, "/api/product/" + userId + "/" + productId);
//        span.finish();
//        try {
//            Product product = productService.updateProduct(userId, productId, updatedProduct);
//            return new ResponseEntity<>(product, HttpStatus.OK);
//        } catch (IllegalArgumentException ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
}
