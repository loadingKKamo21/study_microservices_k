package com.programmingtechie.inventoryservice.repositories.mybatis

import com.programmingtechie.inventoryservice.models.Inventory
import com.programmingtechie.inventoryservice.repositories.InventoryRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InventoryRepositoryImpl(private val inventoryMapper: InventoryMapper) : InventoryRepository {
    
    override fun save(inventory: Inventory): Inventory {
        inventoryMapper.save(inventory)
        return inventory
    }
    
    override fun findById(id: Long): Optional<Inventory> = Optional.ofNullable(inventoryMapper.findById(id))
    
    override fun findBySkuCode(skuCode: String): Optional<Inventory> =
            Optional.ofNullable(inventoryMapper.findBySkuCode(skuCode))
    
    override fun findAll(): List<Inventory> = inventoryMapper.findAll()
    
    override fun deleteById(id: Long) = inventoryMapper.deleteById(id)
    override fun findAllBySkuCodeIn(skuCodes: List<String>): List<Inventory> =
            inventoryMapper.findAllBySkuCodeIn(skuCodes)
    
}