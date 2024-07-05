package com.programmingtechie.inventoryservice.controllers

import com.programmingtechie.inventoryservice.dtos.InventoryResponse
import com.programmingtechie.inventoryservice.services.InventoryService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/inventories")
class InventoryController(private val inventoryService: InventoryService) {
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun isInStock(@RequestParam skuCodes: List<String>): List<InventoryResponse> = inventoryService.isInStock(skuCodes)
    
}