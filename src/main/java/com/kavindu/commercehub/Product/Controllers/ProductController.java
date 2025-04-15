package com.kavindu.commercehub.Product.Controllers;

import com.kavindu.commercehub.Product.dtos.ProductDto;
import com.kavindu.commercehub.Product.dtos.ProductList;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.models.ProductUpdate;
import com.kavindu.commercehub.Product.service.*;
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


    public ProductController(GetProductService getProductService, GetAllProducts getAllProducts, SerchProducts serchProducts, CreateProduct createProduct, OrderProductsByPriceService orderProductsByPriceService, OrderProductsByName orderProductsByName, UpdateProductService updateProductService, DeleteProductService deleteProductService, ImageHandlingService imageHandlingService) {
        this.getProductService = getProductService;
        this.getAllProducts = getAllProducts;
        this.serchProducts = serchProducts;
        this.createProduct = createProduct;
        this.orderProductsByPriceService = orderProductsByPriceService;
        this.orderProductsByName = orderProductsByName;
        this.updateProductService = updateProductService;
        this.deleteProductService = deleteProductService;
        this.imageHandlingService = imageHandlingService;
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
    public ResponseEntity<?> createProduct(
            @RequestPart("product") Product product,
            @RequestPart("image") MultipartFile file
    ) {
        return createProduct.execute(product,file);
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


    @PostMapping(value = "/product-image/upload/{productId}",
                    consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadProductImage(@PathVariable("productId")UUID productId,
                                   @RequestParam("file")MultipartFile file){
        imageHandlingService.uploadProductImage(productId,file);
    }

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
