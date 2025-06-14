package org.bruwave.abacusflow.portal.web.transaction

import org.bruwave.abacusflow.portal.web.model.BasicSaleOrderVO
import org.bruwave.abacusflow.portal.web.model.OrderStatusVO
import org.bruwave.abacusflow.portal.web.model.SaleOrderItemInputVO
import org.bruwave.abacusflow.portal.web.model.SaleOrderItemVO
import org.bruwave.abacusflow.portal.web.model.SaleOrderVO
import org.bruwave.abacusflow.usecase.transaction.BasicSaleOrderTO
import org.bruwave.abacusflow.usecase.transaction.SaleItemInputTO
import org.bruwave.abacusflow.usecase.transaction.SaleOrderItemTO
import org.bruwave.abacusflow.usecase.transaction.SaleOrderTO


fun BasicSaleOrderTO.toBasicVO(): BasicSaleOrderVO = BasicSaleOrderVO(
    id = id,
    orderNo = orderNo.toString(),
    customerName = customerName,
    status = OrderStatusVO.valueOf(status),
    totalAmount = totalAmount,
    totalQuantity = totalQuantity,
    itemCount = itemCount,
    orderDate = orderDate,
)

fun SaleOrderTO.toVO(): SaleOrderVO = SaleOrderVO(
    id = id,
    orderNo = orderNo.toString(),
    customerId = customerId,
    status = OrderStatusVO.valueOf(status),
    orderItems = items.map { it.toVO() },
    note = "null",//TODO-NULL
    createdAt = createdAt.toEpochMilli(),
    updatedAt = updatedAt.toEpochMilli(),
    customerName = "null",//TODO-NULL
    orderDate = orderDate
)

fun SaleOrderItemTO.toVO(): SaleOrderItemVO = SaleOrderItemVO(
    id = id,
    productId = productId,
    productName = "null", //TODO-NULL
    quantity = quantity,
    unitPrice = unitPrice,
    subtotal = subtotal
)


fun SaleOrderItemInputVO.toInputTO(): SaleItemInputTO = SaleItemInputTO(
    productId = productId,
    quantity = quantity,
    unitPrice = unitPrice
)
