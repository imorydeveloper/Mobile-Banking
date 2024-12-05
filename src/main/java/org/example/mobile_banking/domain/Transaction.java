package org.example.mobile_banking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(name = "transaction_at")
    private LocalDateTime transactionAt;
    @Column(name = "transaction_types",nullable = false,length = 30)
    private String transactionTypes; // payment , transfer
    @Column(name = "payment_receiver")
    private String paymentReceiver;
    @Column(nullable = false)
    private Boolean status;
    @Column(name = "is_deleted",nullable = false)
    private Boolean isDeleted;

    @ManyToOne
    private Account owner;

    @ManyToOne
    private Account receiver;
}
