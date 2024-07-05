package com.programmingtechie.orderservice.models

class Order {
    
    private constructor() : this(null, "")
    
    constructor(
            id: Long? = null,
            orderNumber: String
    ) {
        this.id = id
        this.orderNumber = orderNumber
        this.orderLineItemsIdList = ArrayList()
    }
    
    var id: Long?
        private set
    var orderNumber: String
        private set
    var orderLineItemsIdList: MutableList<Long>
        private set
    
}