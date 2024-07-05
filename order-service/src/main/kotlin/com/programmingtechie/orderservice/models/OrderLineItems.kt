package com.programmingtechie.orderservice.models

class OrderLineItems {
    
    private constructor() : this(null, 0, "", 0.0, 0)
    
    constructor(
            id: Long? = null,
            orderId: Long,
            skuCode: String,
            price: Double,
            quantity: Int
    ) {
        this.id = id
        this.orderId = orderId
        this.skuCode = skuCode
        this.price = price
        this.quantity = quantity
    }
    
    var id: Long?
        private set
    var orderId: Long
        private set
    var skuCode: String
        private set
    var price: Double
        private set
    var quantity: Int
        private set
    
}
