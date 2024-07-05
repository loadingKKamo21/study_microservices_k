package com.programmingtechie.orderservice.repositories

import com.programmingtechie.orderservice.models.Order
import com.programmingtechie.orderservice.repositories.mybatis.OrderMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.random.Random

@SpringBootTest
@Transactional
@ActiveProfiles("test")
internal class OrderRepositoryTest {
    
    @Autowired
    lateinit var orderRepository: OrderRepository
    
    @Autowired
    lateinit var orderMapper: OrderMapper
    
    @Test
    @DisplayName("save")
    fun save() {
        //Given
        val order = getOrder()
        
        //When
        val savedOrder = orderRepository.save(order)
        
        //Then
        val findOrder = orderMapper.findById(savedOrder.id!!)
        
        assertThat(findOrder.id).isEqualTo(savedOrder.id)
        assertThat(findOrder.orderNumber).isEqualTo(savedOrder.orderNumber)
    }
    
    @Test
    @DisplayName("findById")
    fun findById() {
        //Given
        val order = getOrder()
        orderMapper.save(order)
        val id = order.id!!
        
        //When
        val findOrder = orderRepository.findById(id).get()
        
        //Then
        assertThat(findOrder.id).isEqualTo(order.id)
        assertThat(findOrder.orderNumber).isEqualTo(order.orderNumber)
    }
    
    @Test
    @DisplayName("findAll")
    fun findAll() {
        //Given
        val size = Random.nextInt(20) + 10
        for (i in 0 until size) {
            val order = getOrder()
            orderMapper.save(order)
        }
        
        //When
        val findOrders = orderRepository.findAll().sortedBy { it.id }
        
        //Then
        val orders = orderMapper.findAll()
        for (i in 0 until size) {
            val order = orders[i]
            val findOrder = findOrders[i]
            
            assertThat(findOrder.id).isEqualTo(order.id)
            assertThat(findOrder.orderNumber).isEqualTo(order.orderNumber)
        }
    }
    
    @Test
    @DisplayName("deleteById")
    fun deleteById() {
        //Given
        val order = getOrder()
        orderMapper.save(order)
        val id = order.id!!
        
        //When
        orderRepository.deleteById(id)
        
        //Then
        val deletedOrder = orderMapper.findById(id)
        
        assertThat(deletedOrder).isNull()
    }
    
    private fun getOrder() = Order(orderNumber = UUID.randomUUID().toString())
    
}