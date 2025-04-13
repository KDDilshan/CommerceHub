package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductDaoWithIaage;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import com.kavindu.commercehub.S3Bucket.S3Buckets;
import com.kavindu.commercehub.S3Bucket.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageHandling {

    private final static Logger logger= LoggerFactory.getLogger(GetProductService.class);

    private final ProductRepository productRepository;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final ProductDaoWithIaage productDaoWithIaage;

    public ImageHandling(ProductRepository productRepository, S3Service s3Service, S3Buckets s3Buckets, ProductDaoWithIaage productDaoWithIaage) {
        this.productRepository = productRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
        this.productDaoWithIaage = productDaoWithIaage;
    }

    public void uploadProdcutImage(UUID productID,MultipartFile file)
    {
        CheakifproductExist(productID);
        String profileImageId = UUID.randomUUID().toString();
        try {
            s3Service.putObject(
                    s3Buckets.getCustomer(),
                    "profile-images/%s/%s".formatted(productID, profileImageId),
                    file.getBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //TODO : Store image ProfileId to  postgres
//        customerDao.updateCustomerProfileImageId(profileImageId,customerId);
    }

    public byte[] getProductImage(UUID productID) {
        var product = productRepository.findById(productID)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with id [%s] not found".formatted(productID)
                ));

        String imageName = product.getImageName();
        if (imageName == null || imageName.isBlank()) {
            throw new ProductNotFoundException(
                    "Product with id [%s] profile image not found".formatted(productID)
            );
        }

        String key = "product-images/%s/%s".formatted(productID, imageName);
        return s3Service.getObject(s3Buckets.getCustomer(), key);
    }






    private ResponseEntity<ProductList> CheakifproductExist(UUID uuid) {
        Optional<Product> product=productRepository.findById(uuid);
        if(product.isPresent()){
            return ResponseEntity.ok(new ProductList(product.get()));
        }
        return null;
    }

}
