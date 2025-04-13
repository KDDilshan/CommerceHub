package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteProductService implements Querry<UUID,String> {

    private final static Logger logger= LoggerFactory.getLogger(DeleteProductService.class);

    private final ProductRepository productRepository;

    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<String> execute(UUID uuid) {
        logger.info("Deleting product with id {}", uuid);
        Product deletingProduct = productRepository.findById(uuid).orElse(null);
        if (deletingProduct!=null) {
            productRepository.delete(deletingProduct);
            logger.info("Deleted product with id {}", uuid);
            return ResponseEntity.ok("Product deleted successfully");
        }

        logger.info("Product with id {} not found", uuid);
        throw new ProductNotFoundException("product with id [%s] not found".formatted(uuid));

    }
}
