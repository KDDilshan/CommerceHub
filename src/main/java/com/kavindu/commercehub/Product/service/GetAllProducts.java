package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductDto;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetAllProducts implements Querry<Integer, List<ProductList>> {

    private final static Logger logger= LoggerFactory.getLogger(GetAllProducts.class);
    private final ProductRepository productRepository;

    public GetAllProducts(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable("productCache")
    public ResponseEntity<List<ProductList>> execute(Integer limit) {
        try {
            logger.info("getAllProducts called");
            if (limit == null || limit <= 0) {
                logger.warn("Invalid limit: {}", limit);
                return ResponseEntity.badRequest().body(null);
            }

            Page<Product> products=productRepository.findAll(PageRequest.of(0, limit));
            List<ProductList> productDtos=products.stream().map(ProductList::new).toList();
            return ResponseEntity.ok(productDtos);
        }catch (Exception e) {
            logger.info("getAllProducts failed");
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
