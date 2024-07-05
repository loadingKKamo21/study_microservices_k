package com.programmingtechie.productservice.repositories.mybatis

import com.programmingtechie.productservice.models.Product
import com.programmingtechie.productservice.repositories.ProductRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ProductRepositoryImpl(private val productMapper: ProductMapper) : ProductRepository {
    
    override fun save(product: Product): Product {
        productMapper.save(product)
        return product
    }
    
    override fun findById(id: Long): Optional<Product> = Optional.ofNullable(productMapper.findById(id))
    
    override fun findAll(): List<Product> = productMapper.findAll()
    
    override fun deleteById(id: Long) = productMapper.deleteById(id)
    
}