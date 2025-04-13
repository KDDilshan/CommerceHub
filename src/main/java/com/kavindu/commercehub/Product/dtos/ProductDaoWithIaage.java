package com.kavindu.commercehub.Product.dtos;

import com.kavindu.commercehub.Product.models.Category;
import com.kavindu.commercehub.Product.models.Product;
import com.kavindu.commercehub.Product.models.Region;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import java.util.UUID;
import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDaoWithIaage{
    private UUID id;
    private String description;
    private Double price;
    private Region region;
    private String manufacturer;
    private String image;
    private Category category;
}
