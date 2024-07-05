package com.programmingtechie.orderservice.repositories.mybatis

import com.programmingtechie.orderservice.models.OrderLineItems
import com.programmingtechie.orderservice.repositories.OrderLineItemsRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class OrderLineItemsRepositoryImpl(private val orderLineItemsMapper: OrderLineItemsMapper) : OrderLineItemsRepository {
    
    override fun save(orderLineItems: OrderLineItems): OrderLineItems {
        orderLineItemsMapper.save(orderLineItems)
        return orderLineItems
    }
    
    override fun saveAll(orderLineItemsList: List<OrderLineItems>): List<OrderLineItems> {
        orderLineItemsMapper.saveAll(orderLineItemsList)
//        orderLineItemsList.forEach { orderLineItemsMapper.save(it) }
        return orderLineItemsList
    }
    
    override fun findById(id: Long): Optional<OrderLineItems> = Optional.ofNullable(orderLineItemsMapper.findById(id))
    
    override fun findAll(): List<OrderLineItems> = orderLineItemsMapper.findAll()
    
    override fun findAll(ids: List<Long>): List<OrderLineItems> = orderLineItemsMapper.findAllByIds(ids)
    
    override fun deleteById(id: Long) = orderLineItemsMapper.deleteById(id)
    
    override fun deleteAllByIds(ids: List<Long>) = orderLineItemsMapper.deleteAllByIds(ids)
    
}