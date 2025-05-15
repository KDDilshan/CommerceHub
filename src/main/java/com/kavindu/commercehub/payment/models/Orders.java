package com.kavindu.commercehub.payment.models;

import com.kavindu.commercehub.Authentication.models.AppUser;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.Date;

public class Orders {
    @Id
    private int id;

    private Date date;

    private String status;

    private int total_amount;

    @OneToOne
    private AppUser user;


  
}
