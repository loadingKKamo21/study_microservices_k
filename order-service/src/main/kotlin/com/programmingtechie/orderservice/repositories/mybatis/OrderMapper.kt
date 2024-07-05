package com.programmingtechie.orderservice.repositories.mybatis

import com.programmingtechie.orderservice.models.Order
import org.apache.ibatis.annotations.Mapper

@Mapper
interface OrderMapper {
    fun save(param: Order): Unit
    fun findById(id: Long): Order
    fun findAll(): List<Order>
    fun deleteById(id: Long): Unit
}
