package com.kavindu.commercehub.Authentication.Dto;

import com.kavindu.commercehub.Product.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private int id;
    private String email;
    private List<Product> products;

    public UserProfileResponse(Integer id,String email, byte[] imageBytes, List<Product> products) {
        this.id=id;
        this.email = email;
        this.products = products;
    }
}
