package com.programmingtechie.productservice.controllers

import com.google.gson.Gson
import com.programmingtechie.productservice.dtos.ProductRequest
import com.programmingtechie.productservice.dtos.ProductResponse
import com.programmingtechie.productservice.services.ProductService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.random.Random

@WebMvcTest(ProductController::class)
@ActiveProfiles("test")
internal class ProductControllerTest {
    
    val GSON = Gson()
    
    @MockBean
    lateinit var productService: ProductService
    
    @Autowired
    lateinit var mockMvc: MockMvc
    lateinit var productResponses: MutableList<ProductResponse>
    
    @BeforeEach
    fun setup() {
        productResponses = ArrayList()
        
        val size = 10
        for (i in 1..size) {
            val productResponse = ProductResponse(id = i.toLong(),
                                                  name = "Product${String.format("%0${size.toString().length}d", i)}",
                                                  description = "Product${String.format("%0${size.toString().length}d", i)}",
                                                  price = Random.nextInt(100) + 10.0)
            productResponses.add(productResponse)
        }
        
        doNothing().`when`(productService).createProduct(any<ProductRequest>())
        `when`(productService.getAllProducts()).thenReturn(productResponses)
    }
    
    @Test
    @DisplayName("createProduct")
    fun createProduct() {
        //Given
        
        //When
        val actions = mockMvc.perform(post("/api/products")
                                              .contentType(APPLICATION_JSON_VALUE)
                                              .content(GSON.toJson(ProductRequest(name = "iPhone 13", description = "iPhone 13", price = 1200.0))))
        
        //Then
        actions.andExpect(status().isCreated)
                .andDo(print())
        
        verify(productService, times(1)).createProduct(any<ProductRequest>())
    }
    
    @Test
    @DisplayName("getAllProducts")
    fun getAllProducts() {
        //Given
        
        //When
        val actions = mockMvc.perform(get("/api/products"))
        
        //Then
        actions.andExpect(status().isOk)
                .andExpect(content().json(GSON.toJson(productResponses)))
                .andDo(print())
        
        verify(productService, times(1)).getAllProducts()
    }
    
}