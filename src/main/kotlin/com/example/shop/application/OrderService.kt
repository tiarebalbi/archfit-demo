package com.example.shop.application

import com.example.shop.domain.Order

interface OrderRepository {
    fun find(id: String): Order?
    fun save(order: Order): Order
}

class OrderService(private val repository: OrderRepository) {
    fun confirm(id: String): Order {
        val order = repository.find(id) ?: error("order $id not found")
        return repository.save(order.confirm())
    }
}
