package com.programmingtechie.inventoryservice.repositories

import com.programmingtechie.inventoryservice.models.Inventory
import com.programmingtechie.inventoryservice.repositories.mybatis.InventoryMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@SpringBootTest
@Transactional
@ActiveProfiles("test")
internal class InventoryRepositoryTest {
    
    @Autowired
    lateinit var inventoryRepository: InventoryRepository
    
    @Autowired
    lateinit var inventoryMapper: InventoryMapper
    
    @Test
    @DisplayName("save")
    fun save() {
        //Given
        val inventory = getInventory()
        
        //When
        val savedInventory = inventoryRepository.save(inventory);
        
        //Then
        val findInventory = inventoryMapper.findById(savedInventory.id!!)
        
        assertThat(findInventory.id).isEqualTo(savedInventory.id)
        assertThat(findInventory.skuCode).isEqualTo(savedInventory.skuCode)
        assertThat(findInventory.quantity).isEqualTo(savedInventory.quantity)
    }
    
    @Test
    @DisplayName("findById")
    fun findById() {
        //Given
        val inventory = getInventory()
        inventoryMapper.save(inventory)
        val id = inventory.id!!
        
        //When
        val findInventory = inventoryRepository.findById(id).get()
        
        //Then
        assertThat(findInventory.id).isEqualTo(inventory.id)
        assertThat(findInventory.skuCode).isEqualTo(inventory.skuCode)
        assertThat(findInventory.quantity).isEqualTo(inventory.quantity)
    }
    
    @Test
    @DisplayName("findBySkuCode")
    fun findBySkuCode() {
        //Given
        val inventory = getInventory()
        inventoryMapper.save(inventory)
        val skuCode = inventory.skuCode
        
        //When
        val findInventory = inventoryRepository.findBySkuCode(skuCode).get()
        
        //Then
        assertThat(findInventory.id).isEqualTo(inventory.id)
        assertThat(findInventory.skuCode).isEqualTo(inventory.skuCode)
        assertThat(findInventory.quantity).isEqualTo(inventory.quantity)
    }
    
    @Test
    @DisplayName("findAll")
    fun findAll() {
        //Given
        val size = Random.nextInt(20) + 10
        for (i in 0 until size) {
            val inventory = getInventory()
            inventoryMapper.save(inventory)
        }
        
        //When
        val findInventories = inventoryRepository.findAll().sortedBy { it.id }
        
        //Then
        val inventories = inventoryMapper.findAll()
        for (i in 0 until size) {
            val inventory = inventories[i]
            val findInventory = findInventories[i]
            
            assertThat(findInventory.id).isEqualTo(inventory.id)
            assertThat(findInventory.skuCode).isEqualTo(inventory.skuCode)
            assertThat(findInventory.quantity).isEqualTo(inventory.quantity)
        }
    }
    
    @Test
    @DisplayName("deleteById")
    fun deleteById() {
        //Given
        val inventory = getInventory()
        inventoryMapper.save(inventory)
        val id = inventory.id!!
        
        //When
        inventoryRepository.deleteById(id)
        
        //Then
        val deletedInventory = inventoryMapper.findById(id)
        
        assertThat(deletedInventory).isNull()
    }
    
    private fun getInventory() = Inventory(skuCode = "iphone_13", quantity = 100)
    
}