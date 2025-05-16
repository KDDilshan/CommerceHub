package com.kavindu.commercehub.payment.models;

import com.kavindu.commercehub.payment.models.enums.PaymentStatus;
import com.kavindu.commercehub.payment.models.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {
    @Id
    @UuidGenerator
    private UUID id;

    @NotNull(message = "add suitable payment type")
    private Enum<PaymentType> Payment_method;

    @NotNull(message = "Required Payment Status")
    private Enum<PaymentStatus> payment_status;

    @NotNull(message = "Stripe Id not added")
    private String TransactionId;

    @CreationTimestamp
    private Date paid_at;

    @OneToOne
    private Orders orders;
}
