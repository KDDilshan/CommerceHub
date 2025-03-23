package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductDto;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SerchProducts implements Querry<String, List<ProductDto>> {
    private final ProductRepository prepository;

    public SerchProducts(ProductRepository prepository) {
        this.prepository = prepository;
    }

    @Override
    public ResponseEntity<List<ProductDto>> execute(String description) {
        try {
           List<Product> product=prepository.searchByDescription(description);
           List<ProductDto> productDtos=product.stream().map(ProductDto::new).toList();
           return ResponseEntity.ok(productDtos);
        }catch (Exception e) {
            throw new ProductNotFoundException();
        }
    }
}
