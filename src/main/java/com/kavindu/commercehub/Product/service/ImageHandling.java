package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Product.Repositories.ProductRepository;
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

    public ImageHandling(ProductRepository productRepository, S3Service s3Service, S3Buckets s3Buckets) {
        this.productRepository = productRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
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



    private ResponseEntity<ProductList> CheakifproductExist(UUID uuid) {
        Optional<Product> product=productRepository.findById(uuid);
        if(product.isPresent()){
            return ResponseEntity.ok(new ProductList(product.get()));
        }
        return null;
    }

}
