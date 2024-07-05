package com.programmingtechie.inventoryservice

import com.programmingtechie.inventoryservice.models.Inventory
import com.programmingtechie.inventoryservice.repositories.InventoryRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile

@SpringBootApplication
@EnableEurekaClient
class InventoryServiceApplication {
	@Bean
	@Profile("default")
	fun loadData(inventoryRepository: InventoryRepository): CommandLineRunner =
			CommandLineRunner { args ->
				inventoryRepository.save(Inventory(skuCode = "iphone_13", quantity = 100))
				inventoryRepository.save(Inventory(skuCode = "iphone_13_red", quantity = 0))
			}
}

fun main(args: Array<String>) {
	runApplication<InventoryServiceApplication>(*args)
}
