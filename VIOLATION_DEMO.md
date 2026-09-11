# Violation demo (for LOCAL-RUN SLOT 2)

1. First run the green pass:  ./gradlew test   → all 4 rules pass (SLOT 1)
2. Add this file, then re-run ./gradlew test — the rule
   "web adapters never touch persistence directly" fails the build (SLOT 2):

src/main/kotlin/com/example/shop/adapter/web/OrderAdminController.kt
---
package com.example.shop.adapter.web

import com.example.shop.adapter.persistence.InMemoryOrderRepository

class OrderAdminController(private val repository: InMemoryOrderRepository) {
    fun forceConfirm(id: String): String {
        val order = repository.find(id) ?: error("order $id not found")
        return repository.save(order.confirm()).status.name
    }
}
---
3. Check that the failure output includes the because(...) rationale text;
   the post's "rationale rides along in the failure" sentence assumes it does.
4. Delete the file to go back to green.

Requires: JDK 17+, network access to Maven Central (first run downloads
the Kotlin 2.1.20 Gradle plugin and ArchUnit 1.4.2).
