package com.programmingtechie.inventoryservice.models

class Inventory {
    
    private constructor() : this(null, "", 0)
    
    constructor(
            id: Long? = null,
            skuCode: String,
            quantity: Int
    ) {
        this.id = id
        this.skuCode = skuCode
        this.quantity = quantity
    }
    
    var id: Long?
        private set
    var skuCode: String
        private set
    var quantity: Int
        private set
    
}