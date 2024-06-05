package com.assignment.inventory.repository;

import com.assignment.inventory.model.InventoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryDetailsRepository extends JpaRepository<InventoryDetails,Long> {
}
