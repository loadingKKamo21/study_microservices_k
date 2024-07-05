package com.programmingtechie.inventoryservice.repositories

import com.programmingtechie.inventoryservice.models.Inventory
import java.util.*

interface InventoryRepository {
    fun save(inventory: Inventory): Inventory
    fun findById(id: Long): Optional<Inventory>
    fun findBySkuCode(skuCode: String): Optional<Inventory>
    fun findAll(): List<Inventory>
    fun deleteById(id: Long): Unit
    fun findAllBySkuCodeIn(skuCodes: List<String>): List<Inventory>
}