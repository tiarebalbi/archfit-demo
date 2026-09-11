package com.example.shop.domain

/**
 * An order in the shop domain.
 *
 * Part of the stable core: per the architecture constitution, this class must never
 * depend on the `application` or `adapter` layers.
 */
data class Order(val id: String, val amountCents: Long, val status: OrderStatus) {
    /** Returns a copy of this order transitioned to [OrderStatus.CONFIRMED]. */
    fun confirm(): Order = copy(status = OrderStatus.CONFIRMED)
}

/** Lifecycle states an [Order] can be in. */
enum class OrderStatus { PENDING, CONFIRMED, CANCELLED }
