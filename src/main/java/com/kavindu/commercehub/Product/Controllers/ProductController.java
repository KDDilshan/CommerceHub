package com.kavindu.commercehub.Product.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kavindu.commercehub.Product.dtos.ProductDto;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.dtos.ProductUpdateRequest;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private final ImageHandlingService imageHandlingService;
    private final ObjectMapper objectMapper;


    public ProductController(GetProductService getProductService, GetAllProducts getAllProducts, SerchProducts serchProducts, CreateProduct createProduct, OrderProductsByPriceService orderProductsByPriceService, OrderProductsByName orderProductsByName, UpdateProductService updateProductService, DeleteProductService deleteProductService, ImageHandlingService imageHandlingService, ObjectMapper objectMapper) {
        this.getProductService = getProductService;
        this.getAllProducts = getAllProducts;
        this.serchProducts = serchProducts;
        this.createProduct = createProduct;
        this.orderProductsByPriceService = orderProductsByPriceService;
        this.orderProductsByName = orderProductsByName;
        this.updateProductService = updateProductService;
        this.deleteProductService = deleteProductService;
        this.imageHandlingService = imageHandlingService;
        this.objectMapper = objectMapper;
    }

    @Operation(summary = "Get one product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<ProductList> getOneProduct(@PathVariable UUID id) {
        return getProductService.execute(id);
    }


    @Operation(summary = "Get all products with optional limit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping("/All")
    public ResponseEntity<List<ProductList>> getAllProducts(@RequestParam(defaultValue = "5")int limit) {
        return getAllProducts.execute(limit);
    }


    @Operation(summary = "Search products by description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products matching description retrieved successfully")
    })
    @GetMapping("/serch")
    public ResponseEntity<List<ProductDto>> getProductsBySerch(@RequestParam String description) {
        return serchProducts.execute(description);
    }


    @Operation(summary = "Create a new product with image upload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    @PostMapping(value = "/create",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart("image") MultipartFile file
    ) throws JsonProcessingException {
        Product product = objectMapper.readValue(productJson, Product.class);
        return createProduct.execute(product,file);
    }


    @Operation(summary = "Order products by price ascending or descending")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products ordered by price retrieved successfully")
    })
    @GetMapping("/orderPrice")
    public ResponseEntity<List<ProductList>> getOrderProductsByPrice(@RequestParam(defaultValue = "asc")String order) {
        return orderProductsByPriceService.execute(order);
    }


    @Operation(summary = "Order products by name ascending or descending")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products ordered by name retrieved successfully")
    })
    @GetMapping("/orderName")
    public ResponseEntity<List<ProductList>> getProductSortByName(@RequestParam(defaultValue = "asc")String order) {
        return orderProductsByName.execute(order);
    }


    @Operation(summary = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductUpdateRequest productUpdateRequest) {
        return updateProductService.execute(id,productUpdateRequest);
    }


    @Operation(summary = "Delete a product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        return deleteProductService.execute(id);
    }


    @Operation(summary = "Upload product image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product image uploaded successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PostMapping(value = "/product-image/upload/{productId}",
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadProductImage(@PathVariable("productId")UUID productId,
                                   @RequestParam("file")MultipartFile file){
        imageHandlingService.uploadProductImage(productId,file);
    }


    @Operation(summary = "Get product image by product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product image retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Image not found for product")
    })
    @GetMapping("/product-image/{ProductID}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable("ProductID") UUID productID) {

        byte[] image = imageHandlingService.getProductImage(productID);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }


}
