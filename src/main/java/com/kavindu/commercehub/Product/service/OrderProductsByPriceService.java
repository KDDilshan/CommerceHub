package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductDto;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class OrderProductsByPriceService implements Querry<String , List<ProductList>> {

    private final ProductRepository productRepository;

    public OrderProductsByPriceService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<List<ProductList>> execute(String Order) {
        try{
            Sort sort = Order.equalsIgnoreCase("asc")
                    ? Sort.by(Sort.Order.asc("price"))
                    : Sort.by(Sort.Order.desc("price"));
            List<Product> products = productRepository.findAll(sort);
            List<ProductList> productDtos =products.stream().map(ProductList::new).toList();
            return ResponseEntity.ok(productDtos);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

    }
}
