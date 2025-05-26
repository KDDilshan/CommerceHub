package com.kavindu.commercehub.Product.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DressSize {
    @Id
    @GeneratedValue
    private Long id;

    private String sizeLabel;
    private Integer quantity;

    @ManyToOne
    private Product product;
}
