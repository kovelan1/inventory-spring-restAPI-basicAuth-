package com.assignment.inventory.service;

import com.assignment.inventory.common.Constants;
import com.assignment.inventory.exceptions.ResourceNotFoundException;
import com.assignment.inventory.model.InventoryDetails;
import com.assignment.inventory.model.InventoryMaster;
import com.assignment.inventory.model.TransactionStatus;
import com.assignment.inventory.repository.InventoryDetailsRepository;
import com.assignment.inventory.repository.InventoryMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InventoryServiceImplementation implements InventoryService {

    // Injecting the InventoryMasterRepository
    @Autowired
    InventoryMasterRepository inventoryMasterRepository;

    // Injecting the InventoryDetailsRepository
    @Autowired
    InventoryDetailsRepository inventoryDetailsRepository;

    /**
     * Retrieves all inventory master records.
     *
     * @return a list of all InventoryMaster records
     */
    @Override
    public List<InventoryMaster> getAllInventories() {
        // Retrieve all InventoryMaster records from the repository
        return inventoryMasterRepository.findAll();
    }

    /**
     * Creates a new inventory master record.
     *
     * @param inventoryMaster the inventory master object to create
     * @return the created InventoryMaster object
     */
    @Override
    public InventoryMaster createInventory(InventoryMaster inventoryMaster) {
        // Save the inventoryMaster object to the repository
        return inventoryMasterRepository.save(inventoryMaster);
    }

    /**
     * Updates an existing inventory master record by product ID.
     *
     * @param productId the ID of the product to update
     * @param inventoryMaster the inventory master object with updated information
     * @return the updated InventoryMaster object
     * @throws ResourceNotFoundException if the product is not found
     */
    @Override
    public InventoryMaster updateInventory(Long productId, InventoryMaster inventoryMaster) throws ResourceNotFoundException {
        // Retrieve the existing InventoryMaster by productId or throw an exception if not found
        var inventory = inventoryMasterRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.PRODUCT_NOT_FOUND_ERROR_MESSAGE));

        // Set the productId to the incoming inventoryMaster object
        inventoryMaster.setProductId(productId);

        // Save the updated inventoryMaster object to the repository
        return inventoryMasterRepository.save(inventoryMaster);
    }

    /**
     * Deletes an inventory master record by product ID.
     *
     * @param productId the ID of the product to delete
     */
    @Override
    public void deleteInventory(Long productId) {
        // Delete the InventoryMaster record by productId from the repository
        inventoryMasterRepository.deleteById(productId);
    }

    /**
     * Retrieves an inventory master record by product ID.
     *
     * @param productId the ID of the product to retrieve
     * @return the InventoryMaster record
     * @throws ResourceNotFoundException if the product is not found
     */
    @Override
    public InventoryMaster getInventoryById(Long productId) throws ResourceNotFoundException {
        // Retrieve the InventoryMaster by productId or throw an exception if not found
        var inventory = inventoryMasterRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.PRODUCT_NOT_FOUND_ERROR_MESSAGE));

        // Return the retrieved InventoryMaster object
        return inventory;
    }

    /**
     * Creates a new inventory details record.
     *
     * @param inventoryDetails the inventory details object to create
     * @return the created InventoryDetails object
     */
    @Override
    public InventoryDetails createInventoryDetails(InventoryDetails inventoryDetails) {
        // Save the inventoryDetails object to the repository
        return inventoryDetailsRepository.save(inventoryDetails);
    }

    /**
     * Updates an existing inventory details record by transaction ID.
     *
     * @param transactionId the ID of the transaction to update
     * @param inventoryDetails the inventory details object with updated information
     * @return the updated InventoryDetails object
     * @throws ResourceNotFoundException if the transaction is not found
     */
    @Override
    public InventoryDetails updateInventoryDetail(Long transactionId, InventoryDetails inventoryDetails) throws ResourceNotFoundException {
        // Retrieve the existing InventoryDetails by transactionId or throw an exception if not found
        var inventoryDetail = inventoryDetailsRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.DETAIL_NOT_FOUND_ERROR_MESSAGE));

        // Set the transactionId to the incoming inventoryDetails object
        inventoryDetails.setTransactionId(transactionId);

        // Save the updated inventoryDetails object to the repository
        return inventoryDetailsRepository.save(inventoryDetails);
    }

    /**
     * Deletes an inventory details record by transaction ID.
     *
     * @param transactionId the ID of the transaction to delete
     */
    @Override
    public void deleteInventoryDetail(Long transactionId) {
        // Delete the InventoryDetails record by transactionId from the repository
        inventoryDetailsRepository.deleteById(transactionId);
    }

    /**
     * Retrieves inventory master records based on various filter criteria.
     *
     * @param productName the name of the product
     * @param category the category of the product
     * @param brand the brand of the product
     * @param minPrice the minimum price of the product
     * @param maxPrice the maximum price of the product
     * @param supplierId the ID of the supplier
     * @param dateAdded the date the product was added
     * @param transactionStatus the transaction status of the inventory details
     * @param transactionType the transaction type of the inventory details
     * @return a list of InventoryMaster objects matching the filter criteria
     */
    @Override
    public List<InventoryMaster> getInventoryByFilters(String productName, String category, String brand, Double minPrice, Double maxPrice, Long supplierId, Date dateAdded, TransactionStatus transactionStatus, String transactionType) {
        // Retrieve InventoryMaster records based on the provided filter criteria
        return inventoryMasterRepository.findByFilters(productName, category, brand, minPrice, maxPrice, supplierId, dateAdded, transactionStatus, transactionType);
    }
}
