package com.programmingtechie.orderservice.services

import com.programmingtechie.orderservice.dtos.InventoryResponse
import com.programmingtechie.orderservice.dtos.OrderLineItemsDto
import com.programmingtechie.orderservice.dtos.OrderRequest
import com.programmingtechie.orderservice.models.Order
import com.programmingtechie.orderservice.models.OrderLineItems
import com.programmingtechie.orderservice.repositories.OrderLineItemsRepository
import com.programmingtechie.orderservice.repositories.OrderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Service
@Transactional(readOnly = true)
class OrderService(
        private val orderRepository: OrderRepository,
        private val orderLineItemsRepository: OrderLineItemsRepository,
        private val webClientBuilder: WebClient.Builder
) {
    
    @Transactional
    fun placeOrder(orderRequest: OrderRequest): String {
        val skuCodes = orderRequest.orderLineItemsDtoList.map { it.skuCode }
        val inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventories") {
                    it.queryParam("skuCodes", skuCodes).build()
                }
                .retrieve()
                .bodyToMono(Array<InventoryResponse>::class.java)
                .block()
        
        val allProductsInStock = inventoryResponseArray!!.all { it.isInStock }
        
        if (allProductsInStock) {
            val order = Order(orderNumber = UUID.randomUUID().toString())
            orderRepository.save(order)
            val orderId = order.id!!
            
            val orderLineItems = orderRequest.orderLineItemsDtoList.map { mapToDto(orderId, it) }
            orderLineItemsRepository.saveAll(orderLineItems)
            
            return "Order Placed Successfully"
        } else
            throw IllegalArgumentException("Product is not in stock, please try again later")
    }
    
    private fun mapToDto(orderId: Long, orderLineItemsDto: OrderLineItemsDto): OrderLineItems {
        return OrderLineItems(orderId = orderId,
                              skuCode = orderLineItemsDto.skuCode,
                              price = orderLineItemsDto.price,
                              quantity = orderLineItemsDto.quantity)
    }
    
}