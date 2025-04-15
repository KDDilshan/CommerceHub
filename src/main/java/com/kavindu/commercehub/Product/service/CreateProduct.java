package com.kavindu.commercehub.Product.service;
import com.kavindu.commercehub.Product.Repositories.CategoryRepository;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Category;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import com.kavindu.commercehub.ProfanityAPI.ProfanityService;
import com.kavindu.commercehub.S3Bucket.S3Buckets;
import com.kavindu.commercehub.S3Bucket.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class CreateProduct {

    private static final Logger logger=  LoggerFactory.getLogger(CreateProduct.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProfanityService profanityService;
    private final S3Buckets s3Buckets;
    private final S3Service s3Service;

    public CreateProduct(ProductRepository productRepository, CategoryRepository categoryRepository, ProfanityService profanityService, S3Buckets s3Buckets, S3Service s3Service) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.profanityService = profanityService;
        this.s3Buckets = s3Buckets;
        this.s3Service = s3Service;
    }

    public ResponseEntity<?> execute(Product product, MultipartFile file) {
        logger.info("Creating product : {}",product);
        try {
            Boolean hasProfinity = profanityService.execute(product.getDescription());
            if (hasProfinity) {
                logger.info("product deciption has profanity words");
                return ResponseEntity.badRequest().body("Product description contains profanity.");
            }

            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));


            Product productToSave = Product.builder()
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .region(product.getRegion())
                    .manufacturer(product.getManufacturer())
                    .category(category)
                    .build();

            productRepository.save(productToSave);
            logger.info("Product saved successfully with ID: {}", productToSave.getId());


            String imageName = UUID.randomUUID().toString();
            String key = "product-images/%s/%s".formatted(productToSave.getId(), imageName);
            System.out.println("Uploading to S3 with key: " + key);

            s3Service.putObject(
                    s3Buckets.getCustomer(),
                    key,
                    file.getBytes()
            );

            productToSave.setImageName(imageName);
            productRepository.save(productToSave);

            logger.info("Product image saved successfully");
            return ResponseEntity.ok(new ProductList(productToSave));

        }catch (IOException e){
            throw new RuntimeException("Failed to uplaod image",e);
        }catch (Exception e) {
            e.printStackTrace();
            logger.info("Product creation failed");
            return ResponseEntity.badRequest().body(null);

        }
    }


}
