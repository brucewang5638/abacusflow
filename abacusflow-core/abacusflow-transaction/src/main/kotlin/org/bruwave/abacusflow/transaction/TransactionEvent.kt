package org.bruwave.abacusflow.transaction



class PurchaseOrderCreatedEvent(val orderId: Long, val supplierId: Long)
class PurchaseOrderCompletedEvent(val orderId: Long, val items: List<PurchaseOrderItem>)
class SaleOrderCreatedEvent(val orderId: Long, val customerId: Long?)
class SaleOrderCompletedEvent(val orderId: Long, val items: List<SaleOrderItem>)
