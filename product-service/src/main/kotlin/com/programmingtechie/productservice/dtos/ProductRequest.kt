package com.programmingtechie.productservice.dtos

data class ProductRequest(
        val name: String,
        val description: String?,
        val price: Double
)
