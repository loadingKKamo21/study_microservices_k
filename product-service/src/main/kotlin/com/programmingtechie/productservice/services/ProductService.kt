package com.programmingtechie.productservice.services

import com.programmingtechie.productservice.dtos.ProductRequest
import com.programmingtechie.productservice.dtos.ProductResponse
import com.programmingtechie.productservice.models.Product
import com.programmingtechie.productservice.repositories.ProductRepository
import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ProductService(private val productRepository: ProductRepository) {
    
    private val log: KLogger = KotlinLogging.logger { }
    
    @Transactional
    fun createProduct(productRequest: ProductRequest): Unit {
        val product = Product(name = productRequest.name,
                              description = productRequest.description,
                              price = productRequest.price)
        
        productRepository.save(product)
        log.info { "Product ${product.id} is saved" }
    }
    
    fun getAllProducts(): List<ProductResponse> {
        val products = productRepository.findAll()
        return products.map { mapToProductResponse(it) }
    }
    
    private fun mapToProductResponse(product: Product): ProductResponse =
            ProductResponse(id = product.id, name = product.name, description = product.description,
                            price = product.price)
    
}
