package com.assignment.inventory;

import com.assignment.inventory.exceptions.ResourceNotFoundException;
import com.assignment.inventory.model.InventoryDetails;
import com.assignment.inventory.model.InventoryMaster;
import com.assignment.inventory.model.TransactionStatus;
import com.assignment.inventory.repository.InventoryDetailsRepository;
import com.assignment.inventory.repository.InventoryMasterRepository;
import com.assignment.inventory.service.InventoryServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class InventoryServiceImplementationTest {

    @Mock
    private InventoryMasterRepository inventoryMasterRepository;

    @Mock
    private InventoryDetailsRepository inventoryDetailsRepository;

    @InjectMocks
    private InventoryServiceImplementation inventoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllInventories() {
        // Arrange
        InventoryMaster inventory1 = new InventoryMaster();
        InventoryMaster inventory2 = new InventoryMaster();
        when(inventoryMasterRepository.findAll()).thenReturn(Arrays.asList(inventory1, inventory2));

        // Act
        List<InventoryMaster> result = inventoryService.getAllInventories();

        // Assert
        assertEquals(2, result.size());
        verify(inventoryMasterRepository, times(1)).findAll();
    }

    @Test
    public void testCreateInventory() {
        // Arrange
        InventoryMaster inventory = new InventoryMaster();
        when(inventoryMasterRepository.save(any(InventoryMaster.class))).thenReturn(inventory);

        // Act
        InventoryMaster result = inventoryService.createInventory(inventory);

        // Assert
        assertNotNull(result);
        verify(inventoryMasterRepository, times(1)).save(inventory);
    }

    @Test
    public void testUpdateInventory() throws ResourceNotFoundException {
        // Arrange
        InventoryMaster inventory = new InventoryMaster();
        inventory.setProductId(1L);
        when(inventoryMasterRepository.findById(1L)).thenReturn(Optional.of(inventory));
        when(inventoryMasterRepository.save(any(InventoryMaster.class))).thenReturn(inventory);

        // Act
        InventoryMaster result = inventoryService.updateInventory(1L, inventory);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getProductId());
        verify(inventoryMasterRepository, times(1)).findById(1L);
        verify(inventoryMasterRepository, times(1)).save(inventory);
    }

    @Test
    public void testUpdateInventory_NotFound() {
        // Arrange
        InventoryMaster inventory = new InventoryMaster();
        when(inventoryMasterRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.updateInventory(1L, inventory));
        verify(inventoryMasterRepository, times(1)).findById(1L);
        verify(inventoryMasterRepository, times(0)).save(any(InventoryMaster.class));
    }

    @Test
    public void testDeleteInventory() {
        // Act
        inventoryService.deleteInventory(1L);

        // Assert
        verify(inventoryMasterRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetInventoryById() throws ResourceNotFoundException {
        // Arrange
        InventoryMaster inventory = new InventoryMaster();
        when(inventoryMasterRepository.findById(1L)).thenReturn(Optional.of(inventory));

        // Act
        InventoryMaster result = inventoryService.getInventoryById(1L);

        // Assert
        assertNotNull(result);
        verify(inventoryMasterRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetInventoryById_NotFound() {
        // Arrange
        when(inventoryMasterRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.getInventoryById(1L));
        verify(inventoryMasterRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateInventoryDetails() {
        // Arrange
        InventoryDetails details = new InventoryDetails();
        when(inventoryDetailsRepository.save(any(InventoryDetails.class))).thenReturn(details);

        // Act
        InventoryDetails result = inventoryService.createInventoryDetails(details);

        // Assert
        assertNotNull(result);
        verify(inventoryDetailsRepository, times(1)).save(details);
    }

    @Test
    public void testUpdateInventoryDetail() throws ResourceNotFoundException {
        // Arrange
        InventoryDetails details = new InventoryDetails();
        details.setTransactionId(1L);
        when(inventoryDetailsRepository.findById(1L)).thenReturn(Optional.of(details));
        when(inventoryDetailsRepository.save(any(InventoryDetails.class))).thenReturn(details);

        // Act
        InventoryDetails result = inventoryService.updateInventoryDetail(1L, details);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getTransactionId());
        verify(inventoryDetailsRepository, times(1)).findById(1L);
        verify(inventoryDetailsRepository, times(1)).save(details);
    }

    @Test
    public void testUpdateInventoryDetail_NotFound() {
        // Arrange
        InventoryDetails details = new InventoryDetails();
        when(inventoryDetailsRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> inventoryService.updateInventoryDetail(1L, details));
        verify(inventoryDetailsRepository, times(1)).findById(1L);
        verify(inventoryDetailsRepository, times(0)).save(any(InventoryDetails.class));
    }

    @Test
    public void testDeleteInventoryDetail() {
        // Act
        inventoryService.deleteInventoryDetail(1L);

        // Assert
        verify(inventoryDetailsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetInventoryByFilters() {
        // Arrange
        InventoryMaster inventory1 = new InventoryMaster();
        InventoryMaster inventory2 = new InventoryMaster();
        when(inventoryMasterRepository.findByFilters(anyString(), anyString(), anyString(), anyDouble(), anyDouble(), anyLong(), any(Date.class), any(TransactionStatus.class), anyString()))
                .thenReturn(Arrays.asList(inventory1, inventory2));

        // Act
        List<InventoryMaster> result = inventoryService.getInventoryByFilters("Product1", "Category1", "Brand1", 10.0, 20.0, 1L, new Date(), TransactionStatus.completed, "SALE");

        // Assert
        assertEquals(2, result.size());
        verify(inventoryMasterRepository, times(1)).findByFilters(anyString(), anyString(), anyString(), anyDouble(), anyDouble(), anyLong(), any(Date.class), any(TransactionStatus.class), anyString());
    }
}
