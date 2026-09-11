package com.example.shop.adapter.persistence

import com.example.shop.application.OrderRepository
import com.example.shop.domain.Order

class InMemoryOrderRepository : OrderRepository {
    private val store = mutableMapOf<String, Order>()
    override fun find(id: String): Order? = store[id]
    override fun save(order: Order): Order = order.also { store[order.id] = it }
}
