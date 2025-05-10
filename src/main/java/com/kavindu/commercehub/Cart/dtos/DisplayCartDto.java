package com.kavindu.commercehub.Cart.dtos;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisplayCartDto {
    private int id;
    private String image;
    private String productDetails;
    private double price;
    private int quantity;
    private double total_price;
}
