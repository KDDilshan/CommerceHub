package com.kavindu.commercehub.Product.service;

import com.kavindu.commercehub.Exceptions.ProductNotValidException;
import com.kavindu.commercehub.Product.Repositories.CategoryRepository;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductDto;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Category;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;




@Service
public class CreateProduct implements Querry<Product, ProductList> {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public CreateProduct(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<ProductList> execute(Product product) {
        try {

            if (containsProfanity(product.getDescription())) {
                return ResponseEntity.badRequest().body(null);
            }

            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));


            Product product1 = Product.builder()
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .region(product.getRegion())
                    .manufacturer(product.getManufacturer())
                    .category(category)
                    .build();

            Product product2=productRepository.save(product1);
            return ResponseEntity.ok(new ProductList(product1));

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    private boolean containsProfanity(String text) {
        String apiUrl = "https://api.api-ninjas.com/v1/profanityfilter?text=" + text;

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", "k9PVUsdRYVG7TzZMUJkH+A==OiVvmnEbJdLNIsGw"); // Replace with your actual API key
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

        return response.getBody() != null && response.getBody().contains("true");
    }
}
