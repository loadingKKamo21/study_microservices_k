package com.programmingtechie.orderservice.repositories.mybatis

import com.programmingtechie.orderservice.models.Order
import com.programmingtechie.orderservice.repositories.OrderRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class OrderRepositoryImpl(private val orderMapper: OrderMapper) : OrderRepository {
    
    override fun save(order: Order): Order {
        orderMapper.save(order)
        return order
    }
    
    override fun findById(id: Long): Optional<Order> = Optional.ofNullable(orderMapper.findById(id))
    
    override fun findAll(): List<Order> = orderMapper.findAll()
    
    override fun deleteById(id: Long) = orderMapper.deleteById(id)
    
}