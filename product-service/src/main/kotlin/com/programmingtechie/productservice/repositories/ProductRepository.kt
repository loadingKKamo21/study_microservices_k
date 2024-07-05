package com.programmingtechie.productservice.repositories

import com.programmingtechie.productservice.models.Product
import java.util.*

interface ProductRepository {
    fun save(product: Product): Product
    fun findById(id: Long): Optional<Product>
    fun findAll(): List<Product>
    fun deleteById(id: Long): Unit
}