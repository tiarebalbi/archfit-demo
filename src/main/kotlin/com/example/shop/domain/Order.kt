package com.example.shop.domain

data class Order(val id: String, val amountCents: Long, val status: OrderStatus) {
    fun confirm(): Order = copy(status = OrderStatus.CONFIRMED)
}

enum class OrderStatus { PENDING, CONFIRMED, CANCELLED }
