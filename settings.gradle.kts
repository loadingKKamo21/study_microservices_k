rootProject.name = "microservices"

listOf("product-service", "order-service", "inventory-service", "discovery-server", "api-gateway").forEach {
    include(it)
    project(":$it").projectDir = File("$rootDir/$it")
}