package com.example.shop.adapter.web

import com.example.shop.application.OrderService

/**
 * Web adapter exposing order confirmation.
 *
 * Delegates to [OrderService] — per the architecture constitution, web adapters must
 * never depend on the persistence adapter directly.
 */
class OrderController(private val service: OrderService) {
    /** Confirms the order with [id] and returns the resulting status name. */
    fun confirmOrder(id: String): String = service.confirm(id).status.name
}
