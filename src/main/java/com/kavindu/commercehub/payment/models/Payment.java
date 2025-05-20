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

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Add suitable payment type")
    private PaymentType paymentMethod;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Required payment status")
    private PaymentStatus paymentStatus;

    @NotNull(message = "Stripe ID not added")
    private String transactionId;

    @CreationTimestamp
    private Date paidAt;

    @OneToOne
    @JoinColumn(name = "order_id") // foreign key
    private Orders orders;
}
