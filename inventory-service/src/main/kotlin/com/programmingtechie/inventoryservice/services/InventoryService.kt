package com.programmingtechie.inventoryservice.services

import com.programmingtechie.inventoryservice.dtos.InventoryResponse
import com.programmingtechie.inventoryservice.repositories.InventoryRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class InventoryService(private val inventoryRepository: InventoryRepository) {
    
    private val log = KotlinLogging.logger { }
    
    fun isInStock(skuCodes: List<String>): List<InventoryResponse> {
        log.info { "Wait Started" }
        Thread.sleep(10000)
        log.info { "Wait Ended" }
        return inventoryRepository.findAllBySkuCodeIn(skuCodes).map { InventoryResponse(it.skuCode, it.quantity > 0) }
    }
    
}