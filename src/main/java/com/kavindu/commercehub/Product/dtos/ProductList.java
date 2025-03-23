package com.kavindu.commercehub.Product.dtos;

import com.kavindu.commercehub.Product.models.Category;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.models.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductList {
    private UUID id;
    private String description;
    private Double Price;
    private String manufacturer;
    private Region region;
    private String CategroyName;

    public ProductList(Product product) {
        this.id = product.getId();
        this.description = product.getDescription();
        this.Price = product.getPrice();
        this.manufacturer = product.getManufacturer();
        this.region = product.getRegion();
        this.CategroyName=product.getCategory().getName();
    }
}
