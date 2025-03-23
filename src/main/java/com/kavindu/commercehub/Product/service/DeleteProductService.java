package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteProductService implements Querry<UUID,String> {
    private final ProductRepository productRepository;

    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<String> execute(UUID uuid) {
        Product deletingProduct = productRepository.findById(uuid).orElse(null);
        if (deletingProduct!=null) {
            productRepository.delete(deletingProduct);
            return ResponseEntity.ok("Product deleted successfully");
        }

        throw new ProductNotFoundException();

    }
}
