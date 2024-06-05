package com.assignment.inventory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InventoryMaster {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long productId;

    private String productName;
    private String category;
    private String brand;
    private int stockInHand;
    private double unitPrice;
    private Long supplierId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column( nullable = false, updatable = false)
    private Date dateAdded;

    @Temporal(TemporalType.TIMESTAMP)
    @Column( nullable = false)
    private Date lastUpdated;
    private String remarks;

    @OneToMany(mappedBy = "inventoryMaster", orphanRemoval = true)
    private Set<InventoryDetails> inventoryDetails = new LinkedHashSet<>();

    @PrePersist
    protected void onCreate() {
        dateAdded = new Date();
        lastUpdated = new Date();

    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = new Date();
    }
}
