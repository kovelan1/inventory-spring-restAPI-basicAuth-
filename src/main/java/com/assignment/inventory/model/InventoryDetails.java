package com.assignment.inventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InventoryDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long transactionId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private InventoryMaster inventoryMaster;

    private String transactionType;
    private int quantity;

    @Temporal(TemporalType.TIMESTAMP)
    @Column( nullable = false, updatable = false)
    private Date transactionDate;

    private  String transactionDescription;
    private double transactionAmount;
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;
    @Column( length = 2500)
    private String transactionNotes;

    @PrePersist
    protected void onCreate() {
        transactionDate = new Date();
    }

}
