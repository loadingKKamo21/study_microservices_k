package com.programmingtechie.orderservice.repositories.mybatis

import com.programmingtechie.orderservice.models.OrderLineItems
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface OrderLineItemsMapper {
    fun save(param: OrderLineItems): Unit
    fun saveAll(@Param("params") params: List<OrderLineItems>): Unit
    fun findById(id: Long): OrderLineItems
    fun findAll(): List<OrderLineItems>
    fun findAllByIds(@Param("ids") ids: List<Long>): List<OrderLineItems>
    fun deleteById(id: Long): Unit
    fun deleteAllByIds(@Param("ids") ids: List<Long>): Unit
}
