package com.programmingtechie.inventoryservice.services

import com.programmingtechie.inventoryservice.models.Inventory
import com.programmingtechie.inventoryservice.repositories.mybatis.InventoryMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@ActiveProfiles("test")
internal class InventoryServiceTest {
    
    @Autowired
    lateinit var inventoryService: InventoryService
    
    @Autowired
    lateinit var inventoryMapper: InventoryMapper
    
    @Test
    @DisplayName("isInStock")
    fun isInStock() {
        //Given
        val inventory = getInventory()
        inventoryMapper.save(inventory)
        val skuCodes = listOf(inventory.skuCode)
        
        //When
        val inventoryResponseList = inventoryService.isInStock(skuCodes)
        
        //Then
        val inventoryResponse = inventoryResponseList[0]
        
        assertThat(inventory.skuCode).isEqualTo(inventoryResponse.skuCode)
        assertThat(inventory.quantity > 0).isEqualTo(inventoryResponse.isInStock)
    }
    
    private fun getInventory() = Inventory(skuCode = "iphone_13", quantity = 100)
    
}