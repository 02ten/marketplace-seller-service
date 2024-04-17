package com.seller.sellerservice.service;

import com.seller.sellerservice.entity.Image;
import com.seller.sellerservice.entity.Product;
import com.seller.sellerservice.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image convertToImage(MultipartFile multipartFile, boolean isPreviewImage, Product product) throws IOException {
        Image image = new Image();
        image.setContentType(multipartFile.getContentType());
        image.setPreviewImage(isPreviewImage);
        image.setBytes(multipartFile.getBytes());
        image.setSize(multipartFile.getSize());
        image.setProduct(product);
        image.setOriginalFileName(multipartFile.getOriginalFilename());
        return imageRepository.save(image);
    }
}
