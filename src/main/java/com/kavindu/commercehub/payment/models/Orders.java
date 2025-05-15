package com.kavindu.commercehub.payment.models;

import com.kavindu.commercehub.Authentication.models.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


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

    @OneToOne
    private AppUser user;

}
