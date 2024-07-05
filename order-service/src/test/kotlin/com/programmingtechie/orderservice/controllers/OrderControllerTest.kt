package com.programmingtechie.orderservice.controllers

import com.google.gson.Gson
import com.programmingtechie.orderservice.dtos.OrderLineItemsDto
import com.programmingtechie.orderservice.dtos.OrderRequest
import com.programmingtechie.orderservice.services.OrderService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(OrderController::class)
@ActiveProfiles("test")
internal class OrderControllerTest {
    
    val gson = Gson()
    
    @MockBean
    lateinit var orderService: OrderService
    
    @Autowired
    lateinit var mockMvc: MockMvc
    
    @BeforeEach
    fun setup() {
        doNothing().`when`(orderService).placeOrder(any<OrderRequest>())
    }
    
    @Test
    @DisplayName("placeOrder")
    fun placeOrder() {
        //Given
        
        //When
        val actions = mockMvc.perform(post("/api/orders")
                                              .contentType(APPLICATION_JSON_VALUE)
                                              .content(gson.toJson(getOrderRequest())))
        
        //Then
        actions.andExpect(status().isOk)
                .andExpect(content().string("Order Placed Successfully"))
                .andDo(print())
        
        verify(orderService, times(1)).placeOrder(any<OrderRequest>())
    }
    
    private fun getOrderRequest(): OrderRequest {
        val orderListItemsList: MutableList<OrderLineItemsDto> = ArrayList()
        orderListItemsList.add(OrderLineItemsDto(id = null, skuCode = "iphone_13", price = 1200.0, quantity = 1))
        return OrderRequest(orderListItemsList)
    }
    
}