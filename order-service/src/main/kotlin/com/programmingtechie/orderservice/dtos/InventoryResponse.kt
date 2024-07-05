package com.programmingtechie.orderservice.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class InventoryResponse(
        val skuCode: String,
        @JsonProperty("isInStock") val isInStock: Boolean
)
