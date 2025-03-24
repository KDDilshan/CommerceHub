package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProductsByName implements Querry<String , List<ProductList>> {

    private final static Logger logger= LoggerFactory.getLogger(OrderProductsByName.class);

    private final ProductRepository prepository;

    public OrderProductsByName(ProductRepository prepository) {
        this.prepository = prepository;
    }

    @Override
    public ResponseEntity<List<ProductList>> execute(String Order) {
        try{
            logger.info("Executing order products by name: " + Order);
            Sort  sort=Order.equalsIgnoreCase("asc")
                    ? Sort.by(Sort.Order.asc("description"))
                    : Sort.by(Sort.Order.desc("description"));

            List<Product>products=prepository.findAll(sort);
            List<ProductList> productList=products.stream().map(ProductList::new).toList();
            logger.info("Products list size: " + productList.size());
            return ResponseEntity.ok(productList);
        }catch (Exception e){
            logger.info("Error while executing order products by name: " + Order);
            e.printStackTrace();
            return ResponseEntity.status(500).body(List.of(new ProductList()));
        }

    }
}
