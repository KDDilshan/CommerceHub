package com.kavindu.commercehub.Product.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(generator = "uuild2")
    private UUID id;

    @NotNull(message = "Add Descrption")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "add Price")
    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private Region region;

    @NotNull(message = "add manufacure")
    @Column(nullable = false)
    private String manufacturer;

    @CreationTimestamp
    @Column(updatable = false,name="created_at")
    private Date created_at;

    @UpdateTimestamp
    @Column(name="updated_at")
    private Date updated_at;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Category_id")
    private Category categoryId;
}

