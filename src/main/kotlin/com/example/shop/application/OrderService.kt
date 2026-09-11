package com.example.shop.application

import com.example.shop.domain.Order

/**
 * Port through which the application layer persists and retrieves orders.
 *
 * Implemented by adapters such as
 * [com.example.shop.adapter.persistence.InMemoryOrderRepository]. The application layer
 * depends only on this interface, never on a concrete adapter, so the persistence
 * mechanism stays replaceable.
 */
interface OrderRepository {
    /** Returns the order with [id], or `null` if none exists. */
    fun find(id: String): Order?

    /** Persists [order] and returns the stored instance. */
    fun save(order: Order): Order
}

/**
 * Use case for confirming orders.
 *
 * This is the only path adapters may use to move an order to
 * [com.example.shop.domain.OrderStatus.CONFIRMED] — web adapters must call through here
 * rather than writing to a repository directly.
 */
class OrderService(private val repository: OrderRepository) {
    /**
     * Confirms the order with [id].
     *
     * @throws IllegalStateException if no order with [id] exists.
     */
    fun confirm(id: String): Order {
        val order = repository.find(id) ?: error("order $id not found")
        return repository.save(order.confirm())
    }
}
