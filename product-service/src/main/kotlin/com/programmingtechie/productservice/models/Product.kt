package com.programmingtechie.productservice.models

class Product {
    
    private constructor() : this(null, "", "", 0.0)
    
    constructor(
            id: Long? = null,
            name: String,
            description: String?,
            price: Double
    ) {
        this.id = id
        this.name = name
        this.description = description
        this.price = price
    }
    
    var id: Long?
        private set
    var name: String
        private set
    var description: String?
        private set
    var price: Double
        private set
    
}