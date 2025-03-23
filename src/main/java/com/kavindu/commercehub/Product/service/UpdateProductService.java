package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Exceptions.ErrorMessages;
import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Exceptions.ProductNotValidException;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductDto;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.models.ProductUpdate;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class UpdateProductService implements Querry<ProductUpdate, ProductDto> {

    private final ProductRepository productRepository;

    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDto> execute(ProductUpdate productUpdate) {
        UUID productId = productUpdate.getId();
        Product product = productRepository.findById(productId).get();

        if (product == null) {
            throw new ProductNotFoundException();
        }
//        validator(productUpdate);
        product.setPrice(productUpdate.getProduct().getPrice());
        product.setDescription(productUpdate.getProduct().getDescription());
        product.setManufacturer(productUpdate.getProduct().getManufacturer());
        productRepository.save(product);
        return ResponseEntity.ok(new ProductDto(product));
    }



}
