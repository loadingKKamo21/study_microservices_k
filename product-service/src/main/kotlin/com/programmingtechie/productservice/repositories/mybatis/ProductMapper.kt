package com.programmingtechie.productservice.repositories.mybatis

import com.programmingtechie.productservice.models.Product
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ProductMapper {
    fun save(param: Product): Unit
    fun findById(id: Long): Product
    fun findAll(): List<Product>
    fun deleteById(id: Long): Unit
}
