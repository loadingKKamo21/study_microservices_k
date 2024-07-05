package com.programmingtechie.orderservice.repositories

import com.programmingtechie.orderservice.models.Order
import com.programmingtechie.orderservice.models.OrderLineItems
import com.programmingtechie.orderservice.repositories.mybatis.OrderLineItemsMapper
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
internal class OrderLineItemsRepositoryTest {
    
    @Autowired
    lateinit var orderLineItemsRepository: OrderLineItemsRepository
    
    @Autowired
    lateinit var orderLineItemsMapper: OrderLineItemsMapper
    
    @Autowired
    lateinit var orderMapper: OrderMapper
    
    @Test
    @DisplayName("save")
    fun save() {
        //Given
        val orderLineItems = getOrderLineItems(1)[0]
        
        //When
        val savedOrderLineItems = orderLineItemsRepository.save(orderLineItems)
        
        //Then
        val findOrderLineItems = orderLineItemsMapper.findById(savedOrderLineItems.id!!)
        
        assertThat(findOrderLineItems.id).isEqualTo(savedOrderLineItems.id)
        assertThat(findOrderLineItems.orderId).isEqualTo(savedOrderLineItems.orderId)
        assertThat(findOrderLineItems.skuCode).isEqualTo(savedOrderLineItems.skuCode)
        assertThat(findOrderLineItems.price).isEqualTo(savedOrderLineItems.price)
        assertThat(findOrderLineItems.quantity).isEqualTo(savedOrderLineItems.quantity)
    }
    
    @Test
    @DisplayName("saveAll")
    fun saveAll() {
        //Given
        val size = Random.nextInt(20) + 10
        
        val orderLineItemsList = getOrderLineItems(size)
        
        //When
        val savedOrderLineItemsList = orderLineItemsRepository.saveAll(orderLineItemsList)
        
        //Then
        val findOrderLineItemsList = orderLineItemsMapper.findAll()
        
        for (i in 0 until size) {
            val savedOrderLineItems = savedOrderLineItemsList[i]
            val findOrderLineItems = findOrderLineItemsList[i]
            
            assertThat(findOrderLineItems.id).isEqualTo(savedOrderLineItems.id)
            assertThat(findOrderLineItems.orderId).isEqualTo(savedOrderLineItems.orderId)
            assertThat(findOrderLineItems.skuCode).isEqualTo(savedOrderLineItems.skuCode)
            assertThat(findOrderLineItems.price).isEqualTo(savedOrderLineItems.price)
            assertThat(findOrderLineItems.quantity).isEqualTo(savedOrderLineItems.quantity)
        }
    }
    
    @Test
    @DisplayName("findById")
    fun findById() {
        //Given
        val orderLineItems = getOrderLineItems(1)[0]
        orderLineItemsMapper.save(orderLineItems)
        val id = orderLineItems.id!!
        
        //When
        val findOrderLineItems = orderLineItemsRepository.findById(id).get()
        
        //Then
        assertThat(findOrderLineItems.id).isEqualTo(orderLineItems.id)
        assertThat(findOrderLineItems.orderId).isEqualTo(orderLineItems.orderId)
        assertThat(findOrderLineItems.skuCode).isEqualTo(orderLineItems.skuCode)
        assertThat(findOrderLineItems.price).isEqualTo(orderLineItems.price)
        assertThat(findOrderLineItems.quantity).isEqualTo(orderLineItems.quantity)
    }
    
    @Test
    @DisplayName("findAll")
    fun findAll() {
        //Given
        val size = Random.nextInt(20) + 10
        
        val createdOrderLineItems = getOrderLineItems(size)
        createdOrderLineItems.forEach { orderLineItemsMapper.save(it) }
        
        //When
        val findOrderLineItemsList = orderLineItemsRepository.findAll().sortedBy { it.id }
        
        //Then
        val orderLineItemsList = orderLineItemsMapper.findAll()
        for (i in 0 until size) {
            val orderLineItems = orderLineItemsList[i]
            val findOrderLineItems = findOrderLineItemsList[i]
            
            assertThat(findOrderLineItems.id).isEqualTo(orderLineItems.id)
            assertThat(findOrderLineItems.orderId).isEqualTo(orderLineItems.orderId)
            assertThat(findOrderLineItems.skuCode).isEqualTo(orderLineItems.skuCode)
            assertThat(findOrderLineItems.price).isEqualTo(orderLineItems.price)
            assertThat(findOrderLineItems.quantity).isEqualTo(orderLineItems.quantity)
        }
    }
    
    @Test
    @DisplayName("findAllByIds")
    fun findAllByIds() {
        //Given
        val size = Random.nextInt(20) + 10
        
        val createdOrderLineItems = getOrderLineItems(size)
        orderLineItemsMapper.saveAll(createdOrderLineItems)
        val ids = createdOrderLineItems.map { it.id!! }
        
        //When
        val findOrderLineItemsList = orderLineItemsRepository.findAll(ids).sortedBy { it.id }
        
        //Then
        val orderLineItemsList = orderLineItemsMapper.findAll().filter { ids.contains(it.id!!) }
        for (i in 0 until size) {
            val orderLineItems = orderLineItemsList[i]
            val findOrderLineItems = findOrderLineItemsList[i]
            
            assertThat(findOrderLineItems.id).isEqualTo(orderLineItems.id)
            assertThat(findOrderLineItems.orderId).isEqualTo(orderLineItems.orderId)
            assertThat(findOrderLineItems.skuCode).isEqualTo(orderLineItems.skuCode)
            assertThat(findOrderLineItems.price).isEqualTo(orderLineItems.price)
            assertThat(findOrderLineItems.quantity).isEqualTo(orderLineItems.quantity)
        }
    }
    
    @Test
    @DisplayName("deleteById")
    fun deleteById() {
        //Given
        val orderLineItems = getOrderLineItems(1)[0]
        orderLineItemsMapper.save(orderLineItems)
        val id = orderLineItems.id!!
        
        //When
        orderLineItemsRepository.deleteById(id)
        
        //Then
        val deletedOrderLineItems = orderLineItemsMapper.findById(id)
        
        assertThat(deletedOrderLineItems).isNull()
    }
    
    @Test
    @DisplayName("deleteAllByIds")
    fun deleteAllByIds() {
        //Given
        val size = Random.nextInt(20) + 10
        
        val createdOrderLineItemsList = getOrderLineItems(size)
        orderLineItemsMapper.saveAll(createdOrderLineItemsList)
        val ids = createdOrderLineItemsList.map { it.id!! }
        
        //When
        orderLineItemsMapper.deleteAllByIds(ids)
        
        //Then
        val deletedOrderLineItemsList = orderLineItemsMapper.findAll().filter { ids.contains(it.id) }
        
        assertThat(deletedOrderLineItemsList).isEmpty()
    }
    
    private fun getOrderLineItems(size: Int): List<OrderLineItems> {
        val orderLineItemsList: MutableList<OrderLineItems> = ArrayList()
        
        val order = Order(orderNumber = UUID.randomUUID().toString())
        orderMapper.save(order)
        for (i in 1..size) {
            val orderLineItems = OrderLineItems(orderId = order.id!!,
                                                skuCode = "Product_${String.format("%0${size.toString().length}d", i)}",
                                                price = Random.nextInt(100) + 10.0,
                                                quantity = Random.nextInt(40) + 10)
            orderLineItemsList.add(orderLineItems)
        }
        return orderLineItemsList
    }
    
}