package com.programmingtechie.orderservice.repositories

import com.programmingtechie.orderservice.models.Order
import java.util.*

interface OrderRepository {
    fun save(order: Order): Order
    fun findById(id: Long): Optional<Order>
    fun findAll(): List<Order>
    fun deleteById(id: Long): Unit
}