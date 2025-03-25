package com.kavindu.commercehub.Product.Controllers;

import com.kavindu.commercehub.Product.dtos.ProductDto;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.models.ProductUpdate;
import com.kavindu.commercehub.Product.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product/v1/")
public class ProductController {
    private final GetProductService getProductService;
    private final GetAllProducts getAllProducts;
    private final SerchProducts serchProducts;
    private final CreateProduct createProduct;
    private final OrderProductsByPriceService orderProductsByPriceService;
    private final OrderProductsByName orderProductsByName;
    private final UpdateProductService updateProductService;
    private final DeleteProductService deleteProductService;


    public ProductController(GetProductService getProductService, GetAllProducts getAllProducts, SerchProducts serchProducts, CreateProduct createProduct, OrderProductsByPriceService orderProductsByPriceService, OrderProductsByName orderProductsByName, UpdateProductService updateProductService, DeleteProductService deleteProductService) {
        this.getProductService = getProductService;
        this.getAllProducts = getAllProducts;
        this.serchProducts = serchProducts;
        this.createProduct = createProduct;
        this.orderProductsByPriceService = orderProductsByPriceService;
        this.orderProductsByName = orderProductsByName;
        this.updateProductService = updateProductService;
        this.deleteProductService = deleteProductService;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ProductList> getOneProduct(@PathVariable UUID id) {
        return getProductService.execute(id);
    }

    @GetMapping("/All")
    public ResponseEntity<List<ProductList>> getAllProducts(@RequestParam(defaultValue = "5")int limit) {
        return getAllProducts.execute(limit);
    }

    @GetMapping("/serch")
    public ResponseEntity<List<ProductDto>> getProductsBySerch(@RequestParam String description) {
        return serchProducts.execute(description);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        return createProduct.execute(product);
    }

    @GetMapping("/orderPrice")
    public ResponseEntity<List<ProductList>> getOrderProductsByPrice(@RequestParam(defaultValue = "asc")String order) {
        return orderProductsByPriceService.execute(order);
    }

    @GetMapping("/orderName")
    public ResponseEntity<List<ProductList>> getProductSortByName(@RequestParam(defaultValue = "asc")String order) {
        return orderProductsByName.execute(order);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        ProductUpdate productUpdate=new ProductUpdate();
        productUpdate.setId(id);
        productUpdate.setProduct(product);
        return updateProductService.execute(productUpdate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        return deleteProductService.execute(id);
    }


}
