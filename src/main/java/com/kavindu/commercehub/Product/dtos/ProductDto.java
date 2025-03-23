package com.kavindu.commercehub.Product.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kavindu.commercehub.Product.models.Category;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.models.Region;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private UUID id;
    private String description;
    private Double price;
    private Region region;
    private String manufacturer;
    private Category category;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.region = product.getRegion();
        this.manufacturer = product.getManufacturer();
        this.category = product.getCategory();

    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Region getRegion() {
        return region;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Category getCategory() {
        return category;
    }
}
