package com.assignment.inventory.repository;

import com.assignment.inventory.model.InventoryMaster;
import com.assignment.inventory.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface InventoryMasterRepository extends JpaRepository<InventoryMaster,Long> {

    /**
     *
     * @param productName
     * @param category
     * @param brand
     * @param minPrice
     * @param maxPrice
     * @param supplierId
     * @param dateAdded
     * @param transactionStatus
     * @param transactionType
     * @return
     */
    @Query("SELECT im FROM InventoryMaster im " +
            "JOIN im.inventoryDetails id " +
            "WHERE (:productName IS NULL OR im.productName = :productName) AND " +
            "(:category IS NULL OR im.category = :category) AND " +
            "(:brand IS NULL OR im.brand = :brand) AND " +
            "(:minPrice IS NULL OR im.unitPrice >= :minPrice) AND " +
            "(:maxPrice IS NULL OR im.unitPrice <= :maxPrice) AND " +
            "(:supplierId IS NULL OR im.supplierId = :supplierId) AND " +
            "(:dateAdded IS NULL OR im.dateAdded = :dateAdded) AND " +
            "(:transactionStatus IS NULL OR id.transactionStatus = :transactionStatus) AND "+
            "(:transactionType IS NULL OR id.transactionType = :transactionType)")
    List<InventoryMaster> findByFilters(@Param("productName") String productName,
                                        @Param("category") String category,
                                        @Param("brand") String brand,
                                        @Param("minPrice") Double minPrice,
                                        @Param("maxPrice") Double maxPrice,
                                        @Param("supplierId") Long supplierId,
                                        @Param("dateAdded") Date dateAdded,
                                        @Param("transactionStatus") TransactionStatus transactionStatus,
                                        @Param("transactionType") String transactionType);
}
