package com.assignment.inventory.controller;

import com.assignment.inventory.exceptions.ResourceNotFoundException;
import com.assignment.inventory.model.InventoryDetails;
import com.assignment.inventory.model.InventoryMaster;
import com.assignment.inventory.model.TransactionStatus;
import com.assignment.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory", description = "Inventory management APIs")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    /**
     * Retrieves all inventory master records.
     *
     * @return ResponseEntity containing a list of all InventoryMaster records
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Get all inventories", description = "Retrieve all inventory master records")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = List.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<List<InventoryMaster>> getAllInventories() {
        // Return a response entity with the list of all inventory masters
        return ResponseEntity.ok(inventoryService.getAllInventories());
    }

    /**
     * Retrieves an inventory master record by product ID.
     *
     * @param productId the ID of the product to retrieve
     * @return ResponseEntity containing the InventoryMaster record
     * @throws ResourceNotFoundException if the product is not found
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Get inventory by ID", description = "Retrieve an inventory master record by product ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryMaster.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<InventoryMaster> getByProductId(@PathVariable("id") Long productId) throws ResourceNotFoundException {
        // Return a response entity with the inventory master record
        return ResponseEntity.ok(inventoryService.getInventoryById(productId));
    }

    /**
     * Retrieves inventory masters based on various filter criteria.
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
    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Filter inventories", description = "Retrieve inventory masters based on filter criteria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = List.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public List<InventoryMaster> filterInventoryMasters(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateAdded,
            @RequestParam(required = false) TransactionStatus transactionStatus,
            @RequestParam(required = false) String transactionType) {
        // Return a list of inventory masters based on the filter criteria
        return inventoryService.getInventoryByFilters(productName, category, brand, minPrice, maxPrice, supplierId, dateAdded, transactionStatus, transactionType);
    }

    /**
     * Creates a new inventory master record.
     *
     * @param inventoryMaster the inventory master object to create
     * @return ResponseEntity containing the created InventoryMaster object
     */
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Create inventory master", description = "Create a new inventory master record")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryMaster.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<InventoryMaster> createMasterInventory(@RequestBody InventoryMaster inventoryMaster) {
        // Return a response entity with the created inventory master
        return ResponseEntity.ok(inventoryService.createInventory(inventoryMaster));
    }

    /**
     * Updates an existing inventory master record by product ID.
     *
     * @param productId the ID of the product to update
     * @param inventoryMaster the inventory master object with updated information
     * @return ResponseEntity containing the updated InventoryMaster object
     * @throws ResourceNotFoundException if the product is not found
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update inventory master", description = "Update an existing inventory master record by product ID; Only Admin allowed to update ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryMaster.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<InventoryMaster> updateInventory(@PathVariable("id") Long productId, @RequestBody InventoryMaster inventoryMaster) throws ResourceNotFoundException {
        // Return a response entity with the updated inventory master
        return ResponseEntity.ok(inventoryService.updateInventory(productId, inventoryMaster));
    }

    /**
     * Deletes an inventory master record by product ID.
     *
     * @param productId the ID of the product to delete
     * @return ResponseEntity indicating the result of the delete operation
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete inventory master", description = "Delete an inventory master record by product ID; Only Admin allowed to delete")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryMaster.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<?> deleteInventory(@PathVariable("id") Long productId) {
        // Delete the inventory master and return a response entity
        inventoryService.deleteInventory(productId);
        return ResponseEntity.ok("");
    }

    /**
     * Creates a new inventory details record.
     *
     * @param inventoryDetails the inventory details object to create
     * @return ResponseEntity containing the created InventoryDetails object
     */
    @PostMapping("/details")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @Operation(summary = "Create inventory details", description = "Create a new inventory details record")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryMaster.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<InventoryDetails> createInventoryDetails(@RequestBody InventoryDetails inventoryDetails) {
        // Return a response entity with the created inventory details
        return ResponseEntity.ok(inventoryService.createInventoryDetails(inventoryDetails));
    }

    /**
     * Updates an existing inventory details record by transaction ID.
     *
     * @param transactionId the ID of the transaction to update
     * @param inventoryDetails the inventory details object with updated information
     * @return ResponseEntity containing the updated InventoryDetails object
     * @throws ResourceNotFoundException if the transaction is not found
     */
    @PutMapping("/details/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update inventory details", description = "Update an existing inventory details record by transaction ID; Only Admin allowed to update")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryMaster.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<InventoryDetails> updateInventoryDetail(@PathVariable("id") Long transactionId, @RequestBody InventoryDetails inventoryDetails) throws ResourceNotFoundException {
        // Return a response entity with the updated inventory details
        return ResponseEntity.ok(inventoryService.updateInventoryDetail(transactionId, inventoryDetails));
    }

    /**
     * Deletes an inventory details record by transaction ID.
     *
     * @param transactionId the ID of the transaction to delete
     * @return ResponseEntity indicating the result of the delete operation
     */
    @DeleteMapping("/details/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete inventory details", description = "Delete an inventory details record by transaction ID; Only Admin allowed to delete")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = InventoryMaster.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    public ResponseEntity<?> deleteInventoryDetail(@PathVariable("id") Long transactionId) {
        // Delete the inventory details and return a response entity
        inventoryService.deleteInventoryDetail(transactionId);
        return ResponseEntity.ok("");
    }
}
