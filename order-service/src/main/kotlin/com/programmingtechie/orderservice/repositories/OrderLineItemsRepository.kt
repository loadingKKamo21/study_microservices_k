package com.programmingtechie.orderservice.repositories

import com.programmingtechie.orderservice.models.OrderLineItems
import java.util.*

interface OrderLineItemsRepository {
    fun save(orderLineItems: OrderLineItems): OrderLineItems
    fun saveAll(orderLineItemsList: List<OrderLineItems>): List<OrderLineItems>
    fun findById(id: Long): Optional<OrderLineItems>
    fun findAll(): List<OrderLineItems>
    fun findAll(ids: List<Long>): List<OrderLineItems>
    fun deleteById(id: Long): Unit
    fun deleteAllByIds(ids: List<Long>): Unit
}