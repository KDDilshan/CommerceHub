package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductDto;
import com.kavindu.commercehub.Product.dtos.ProductUpdateRequest;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateProductService{
     private final static Logger logger= LoggerFactory.getLogger(UpdateProductService.class);

    private final ProductRepository productRepository;

    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public ResponseEntity<ProductDto> execute(UUID productId, ProductUpdateRequest updateRequest) {

        logger.info("updateProduct for :{}", productId);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id [%s] not found".formatted(productId)));

        logger.info("updating product :{}", product);

        if (updateRequest.getPrice()!=0) {
            product.setPrice(updateRequest.getPrice());
        }
        if (updateRequest.getDescription() != null) {
            product.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getManufacturer() != null) {
            product.setManufacturer(updateRequest.getManufacturer());
        }

        logger.info("updated product :{}", product);
        productRepository.save(product);
        return ResponseEntity.ok(new ProductDto(product));
    }


}
