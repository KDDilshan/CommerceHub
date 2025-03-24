package com.kavindu.commercehub;

import com.kavindu.commercehub.Product.Repositories.CategoryRepository;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.models.Category;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.CreateProduct;
import com.kavindu.commercehub.ProfanityAPI.ProfanityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.hamcrest.Matchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class createProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProfanityService profanityService;

    @InjectMocks
    private CreateProduct createProduct;

    @Test
    void shouldCreateProductSuccessfully() {
        Product product = new Product("Clean description", 100.0, "USA", "BrandX", new Category(1L, "Electronics"));
        Category category = new Category(1L, "Electronics");


    }
}
