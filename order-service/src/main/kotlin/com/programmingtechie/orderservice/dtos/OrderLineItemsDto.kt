package com.programmingtechie.orderservice.dtos

data class OrderLineItemsDto(
        val id: Long?,
        val skuCode: String,
        val price: Double,
        val quantity: Int
)
