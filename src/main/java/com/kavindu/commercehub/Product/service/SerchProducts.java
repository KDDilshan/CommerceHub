package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductDto;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class SerchProducts implements Querry<String, List<ProductDto>> {
    private final static Logger logger= LoggerFactory.getLogger(SerchProducts.class);
    private final ProductRepository prepository;

    public SerchProducts(ProductRepository prepository) {
        this.prepository = prepository;
    }

    @Override
    public ResponseEntity<List<ProductDto>> execute(String description) {
        try {
            logger.info("searching products by description : {}", description);
           List<Product> product=prepository.searchByDescription(description);
           List<ProductDto> productDtos=product.stream().map(ProductDto::new).toList();
           logger.info("find Product List of names");
           return ResponseEntity.ok(productDtos);
        }catch (Exception e) {
            logger.info("not found product for description : {}", description);
            throw new ProductNotFoundException();
        }
    }
}
