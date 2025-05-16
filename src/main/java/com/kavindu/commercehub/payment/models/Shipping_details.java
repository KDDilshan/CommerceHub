package com.kavindu.commercehub.payment.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Shipping_details {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "add Shipping Address")
    private String shippingAddress;

    @NotNull(message = "enter phone no")
    private String PhoneNo;

    @OneToOne
    private Orders order;

}
