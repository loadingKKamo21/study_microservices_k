package com.programmingtechie.productservice.services

import com.programmingtechie.productservice.dtos.ProductRequest
import com.programmingtechie.productservice.models.Product
import com.programmingtechie.productservice.repositories.mybatis.ProductMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@SpringBootTest
@Transactional
@ActiveProfiles("test")
internal class ProductServiceTest {
    
    @Autowired
    lateinit var productService: ProductService
    
    @Autowired
    lateinit var productMapper: ProductMapper
    
    @Test
    @DisplayName("createProduct")
    fun createProduct() {
        //Given
        val productRequest = getProductRequest()
        
        //When
        productService.createProduct(productRequest)
        
        //Then
        val findProduct = productMapper.findAll()[0]
        
        assertThat(findProduct.name).isEqualTo(productRequest.name)
        assertThat(findProduct.description).isEqualTo(productRequest.description)
        assertThat(findProduct.price).isEqualTo(productRequest.price)
    }
    
    @Test
    @DisplayName("getAllProducts")
    fun getAllProducts() {
        //Given
        val size = Random.nextInt(20) + 10
        for (i in 1..size) {
            val product = Product(name = "Product${String.format("%0${size.toString().length}d", i)}",
                                  description = "Product${String.format("%0${size.toString().length}d", i)}",
                                  price = Random.nextInt(100) + 10.0)
            productMapper.save(product)
        }
        
        //When
        val findProductResponses = productService.getAllProducts().sortedBy { it.id }
        
        //Then
        val products = productMapper.findAll()
        for (i in 0 until size) {
            val product = products[i]
            val findProductResponse = findProductResponses[i]
            
            assertThat(findProductResponse.id).isEqualTo(product.id)
            assertThat(findProductResponse.name).isEqualTo(product.name)
            assertThat(findProductResponse.description).isEqualTo(product.description)
            assertThat(findProductResponse.price).isEqualTo(product.price)
        }
    }
    
    private fun getProductRequest() = ProductRequest(name = "iPhone 13", description = "iPhone 13", price = 1200.0)
    
}