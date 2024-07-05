package com.programmingtechie.orderservice.controllers

import com.programmingtechie.orderservice.dtos.OrderRequest
import com.programmingtechie.orderservice.services.OrderService
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import io.github.resilience4j.timelimiter.annotation.TimeLimiter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/api/orders")
class OrderController(private val orderService: OrderService) {
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    fun placeOrder(@RequestBody orderRequest: OrderRequest): CompletableFuture<String> =
            CompletableFuture.supplyAsync { orderService.placeOrder(orderRequest) }
    
    fun fallbackMethod(orderRequest: OrderRequest, runtimeException: RuntimeException): CompletableFuture<String> =
            CompletableFuture.supplyAsync { "Oops! Something went wrong, please order after some time!" }
    
}