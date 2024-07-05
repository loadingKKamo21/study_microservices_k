package com.programmingtechie.productservice.dtos

data class ProductResponse(
        val id: Long?,
        val name: String,
        val description: String?,
        val price: Double
)