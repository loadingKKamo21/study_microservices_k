package com.programmingtechie.inventoryservice.repositories.mybatis

import com.programmingtechie.inventoryservice.models.Inventory
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface InventoryMapper {
    fun save(param: Inventory): Unit
    fun findById(id: Long): Inventory
    fun findBySkuCode(skuCode: String): Inventory
    fun findAll(): List<Inventory>
    fun deleteById(id: Long): Unit
    fun findAllBySkuCodeIn(@Param("params") params: List<String>): List<Inventory>
}
