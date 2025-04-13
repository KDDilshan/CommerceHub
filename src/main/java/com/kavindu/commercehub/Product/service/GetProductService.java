package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GetProductService implements Querry<UUID, ProductList> {

    private final static Logger logger= LoggerFactory.getLogger(GetProductService.class);

    private final ProductRepository productRepository;

    public GetProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductList> execute(UUID uuid) {
        logger.info("Get product by id: {}", uuid);
        ResponseEntity<ProductList> product = CheakifproductExist(uuid);
        if (product != null) return product;
        logger.info("Product not found");
        throw new ProductNotFoundException("product with id [%s] not found".formatted(uuid));
    }

    private ResponseEntity<ProductList> CheakifproductExist(UUID uuid) {
        Optional<Product> product=productRepository.findById(uuid);
        if(product.isPresent()){
            return ResponseEntity.ok(new ProductList(product.get()));
        }
        return null;
    }
}
