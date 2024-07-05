package com.programmingtechie.orderservice.dtos

data class OrderRequest(
        val orderLineItemsDtoList: List<OrderLineItemsDto>
)
