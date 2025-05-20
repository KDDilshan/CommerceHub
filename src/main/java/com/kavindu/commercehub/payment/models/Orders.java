package com.kavindu.commercehub.payment.models;

import com.kavindu.commercehub.Authentication.models.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date date;

    private String status;

    private int total_amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<Order_items> orderItemsList;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private ShippingDetails  shippingDetails;

}
