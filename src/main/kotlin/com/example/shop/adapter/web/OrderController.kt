package com.example.shop.adapter.web

import com.example.shop.application.OrderService

class OrderController(private val service: OrderService) {
    fun confirmOrder(id: String): String = service.confirm(id).status.name
}
