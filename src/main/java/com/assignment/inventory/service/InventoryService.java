package com.assignment.inventory.service;

import com.assignment.inventory.exceptions.ResourceNotFoundException;
import com.assignment.inventory.model.InventoryDetails;
import com.assignment.inventory.model.InventoryMaster;
import com.assignment.inventory.model.TransactionStatus;

import java.util.Date;
import java.util.List;

public interface InventoryService {

    List<InventoryMaster> getAllInventories();
    InventoryMaster createInventory(InventoryMaster inventoryMaster);
    InventoryMaster updateInventory(Long productId,InventoryMaster inventoryMaster) throws ResourceNotFoundException;
    void deleteInventory(Long productId);

    InventoryMaster getInventoryById(Long productId) throws ResourceNotFoundException;

    InventoryDetails createInventoryDetails(InventoryDetails inventoryDetails);

    InventoryDetails updateInventoryDetail(Long transactionId, InventoryDetails inventoryDetails) throws ResourceNotFoundException;

    void deleteInventoryDetail(Long transactionId);

    List<InventoryMaster> getInventoryByFilters(String productName, String category, String brand, Double minPrice, Double maxPrice, Long supplierId, Date dateAdded, TransactionStatus transactionStatus, String transactionType);
}
