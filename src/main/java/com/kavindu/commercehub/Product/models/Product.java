package com.kavindu.commercehub.Product.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.payment.models.Order_items;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = true)
    private String name;

    @NotNull(message = "Add Descrption")
    @Column(nullable = false)
    private String description;

    @NotNull(message = "add Price")
    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private Region region;

    @Column(nullable = true)
    private Integer quantity;

    @NotNull(message = "add manufacure")
    @Column(nullable = false)
    private String manufacturer;

    @Column(nullable = true)
    private String imageName;

    @CreationTimestamp
    @Column(updatable = false,name="created_at")
    private Date created_at;

    @UpdateTimestamp
    @Column(name="updated_at")
    private Date updated_at;

    @ManyToOne()
    @JoinColumn(name = "Category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = true)
    private AppUser createdBy;

    @OneToMany(mappedBy = "product")
    private List<Order_items> orderItems;


    public Product(String cleanDescription, double v, String usa, String brandX, Category electronics) {
    }

    @ManyToMany
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<DressTags> tags;

}

