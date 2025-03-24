package com.kavindu.commercehub.Product.service;
import com.kavindu.commercehub.Product.Repositories.CategoryRepository;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Category;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.repos.Querry;
import com.kavindu.commercehub.ProfanityAPI.ProfanityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public class CreateProduct implements Querry<Product, ProductList> {

    private static final Logger logger=  LoggerFactory.getLogger(CreateProduct.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProfanityService profanityService;

    public CreateProduct(ProductRepository productRepository, CategoryRepository categoryRepository, ProfanityService profanityService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.profanityService = profanityService;
    }

    @Override
    public ResponseEntity<?> execute(Product product) {
        logger.info("Creating product : {}",product);
        try {
            Boolean hasProfinity=profanityService.execute(product.getDescription());
            if(hasProfinity){
                logger.info("product deciption has profanity words");
                return ResponseEntity.badRequest().body("Product description contains profanity.");
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
            logger.info("Product created");


            productRepository.save(product1);
            logger.info("Product saved successfully with ID: {}",product1.getId());
            return ResponseEntity.ok(new ProductList(product1));

        }catch (Exception e) {
            e.printStackTrace();
            logger.info("Product creation failed");
            return ResponseEntity.badRequest().body(null);

        }
    }


}
