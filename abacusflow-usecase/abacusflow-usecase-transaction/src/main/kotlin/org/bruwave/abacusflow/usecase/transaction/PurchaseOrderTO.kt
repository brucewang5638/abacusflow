package org.bruwave.abacusflow.usecase.transaction

import java.time.Instant
import java.time.LocalDate
import java.util.UUID

data class PurchaseOrderTO(
    val id: Long,
    val orderNo: UUID,
    val supplierId: Long,
    val status: String,
    val orderDate: LocalDate,
    val items: List<PurchaseOrderItemTO>,
    val note: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
) {
    data class PurchaseOrderItemTO(
        val id: Long,
        val productId: Long,
        val quantity: Int,
        val unitPrice: Double,
        val subtotal: Double,
    )
}
