package com.programmingtechie.productservice.controllers

import com.programmingtechie.productservice.dtos.ProductRequest
import com.programmingtechie.productservice.dtos.ProductResponse
import com.programmingtechie.productservice.services.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody productRequest: ProductRequest) {
        productService.createProduct(productRequest)
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllProducts(): List<ProductResponse> = productService.getAllProducts()
    
}