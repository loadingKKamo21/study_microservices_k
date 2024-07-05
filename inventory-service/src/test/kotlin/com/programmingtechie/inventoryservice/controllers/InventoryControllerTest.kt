package com.programmingtechie.inventoryservice.controllers

import com.google.gson.Gson
import com.programmingtechie.inventoryservice.dtos.InventoryResponse
import com.programmingtechie.inventoryservice.services.InventoryService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(InventoryController::class)
@ActiveProfiles("test")
internal class InventoryControllerTest {
    
    val gson = Gson()
    
    @MockBean
    lateinit var inventoryService: InventoryService
    
    @Autowired
    lateinit var mockMvc: MockMvc
    lateinit var inventoryResponseList: List<InventoryResponse>
    
    @BeforeEach
    fun setup() {
        inventoryResponseList = listOf(
                InventoryResponse(skuCode = "iphone_13", isInStock = true),
                InventoryResponse(skuCode = "iphone_13_red", isInStock = false)
        )
        
        `when`(inventoryService.isInStock(anyList())).thenReturn(inventoryResponseList)
    }
    
    @Test
    @DisplayName("isInStock")
    fun isInStock() {
        //Given
        
        //When
        val actions = mockMvc.perform(get("/api/inventories")
                                              .param("skuCodes", "iphone_13", "iphone_13_red"))
        
        //Then
        actions.andExpect(status().isOk)
                .andExpect(content().json(gson.toJson(inventoryResponseList)))
                .andDo(print())
        
        verify(inventoryService, times(1)).isInStock(anyList())
    }
    
}