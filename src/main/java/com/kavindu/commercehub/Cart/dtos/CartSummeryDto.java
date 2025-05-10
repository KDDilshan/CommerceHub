package com.kavindu.commercehub.Cart.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartSummeryDto {
    private List<DisplayCartDto> cartDtoList;
    private double Grand_total;
}
