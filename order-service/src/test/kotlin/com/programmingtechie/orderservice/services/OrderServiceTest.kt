package com.programmingtechie.orderservice.services

import com.programmingtechie.orderservice.dtos.OrderLineItemsDto
import com.programmingtechie.orderservice.dtos.OrderRequest
import com.programmingtechie.orderservice.repositories.mybatis.OrderLineItemsMapper
import com.programmingtechie.orderservice.repositories.mybatis.OrderMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@ActiveProfiles("test")
internal class OrderServiceTest {
    
    @Autowired
    lateinit var orderService: OrderService
    
    @Autowired
    lateinit var orderMapper: OrderMapper
    
    @Autowired
    lateinit var orderLineItemsMapper: OrderLineItemsMapper
    
    @Test
    @DisplayName("placeOrder")
    fun placeOrder() {
        //Given
        val orderRequest = getOrderRequest()
        
        //When
        orderService.placeOrder(orderRequest)
        
        //Then
        val findOrder = orderMapper.findAll()[0]
        val findOrderLineItems = orderLineItemsMapper.findAll()[0]
        
        assertThat(findOrder).isNotNull
        assertThat(findOrderLineItems.orderId).isEqualTo(findOrder.id)
        assertThat(findOrderLineItems.skuCode).isEqualTo(orderRequest.orderLineItemsDtoList[0].skuCode)
        assertThat(findOrderLineItems.price).isEqualTo(orderRequest.orderLineItemsDtoList[0].price)
        assertThat(findOrderLineItems.quantity).isEqualTo(orderRequest.orderLineItemsDtoList[0].quantity)
    }
    
    private fun getOrderRequest(): OrderRequest {
        val orderListItemsDtoList: MutableList<OrderLineItemsDto> = ArrayList()
        orderListItemsDtoList.add(OrderLineItemsDto(id = null, skuCode = "iphone_13", price = 1200.0, quantity = 1))
        return OrderRequest(orderListItemsDtoList)
    }
    
}