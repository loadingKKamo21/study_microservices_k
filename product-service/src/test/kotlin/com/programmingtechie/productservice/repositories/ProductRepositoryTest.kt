package com.programmingtechie.productservice.repositories

import com.programmingtechie.productservice.models.Product
import com.programmingtechie.productservice.repositories.mybatis.ProductMapper
import org.assertj.core.api.Assertions.assertThat
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
internal class ProductRepositoryTest {
    
    @Autowired
    lateinit var productRepository: ProductRepository
    
    @Autowired
    lateinit var productMapper: ProductMapper
    
    @Test
    @DisplayName("save")
    fun save() {
        //Given
        val product = getProduct()
        
        //When
        val savedProduct = productRepository.save(product)
        
        //Then
        val findProduct = productMapper.findById(savedProduct.id!!)
        
        assertThat(findProduct.id).isEqualTo(savedProduct.id)
        assertThat(findProduct.name).isEqualTo(savedProduct.name)
        assertThat(findProduct.description).isEqualTo(savedProduct.description)
        assertThat(findProduct.price).isEqualTo(savedProduct.price)
    }
    
    @Test
    @DisplayName("findById")
    fun findById() {
        //Given
        val product = getProduct()
        productMapper.save(product)
        val id = product.id!!
        
        //When
        val findProduct = productRepository.findById(id).get()
        
        //Then
        assertThat(findProduct.id).isEqualTo(product.id)
        assertThat(findProduct.name).isEqualTo(product.name)
        assertThat(findProduct.description).isEqualTo(product.description)
        assertThat(findProduct.price).isEqualTo(product.price)
    }
    
    @Test
    @DisplayName("findAll")
    fun findAll() {
        //Given
        val size = Random.nextInt(20) + 10
        for (i in 1..size) {
            val product = Product(name = "Product${String.format("%0${size.toString().length}d", i)}",
                                  description = "Product${String.format("%0${size.toString().length}d", i)}",
                                  price = Random.nextInt(100) + 10.0)
            productMapper.save(product)
        }
        
        //When
        val findProducts = productRepository.findAll().sortedBy { it.id }
        
        //Then
        val products = productMapper.findAll()
        for (i in 0 until size) {
            val product = products[i]
            val findProduct = findProducts[i]
            
            assertThat(findProduct.id).isEqualTo(product.id)
            assertThat(findProduct.name).isEqualTo(product.name)
            assertThat(findProduct.description).isEqualTo(product.description)
            assertThat(findProduct.price).isEqualTo(product.price)
        }
    }
    
    @Test
    @DisplayName("deleteById")
    fun deleteById() {
        //Given
        val product = getProduct()
        productMapper.save(product)
        val id = product.id!!
        
        //When
        productRepository.deleteById(id)
        
        //Then
        val deletedProduct = productMapper.findById(id)
        
        assertThat(deletedProduct).isNull()
    }
    
    private fun getProduct() = Product(name = "iPhone 13", description = "iPhone 13", price = 1200.0)
    
}