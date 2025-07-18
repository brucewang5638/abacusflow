package org.bruwave.abacusflow.usecase.partner.mapper

import org.bruwave.abacusflow.partner.Supplier
import org.bruwave.abacusflow.usecase.partner.SupplierTO

fun Supplier.toTO() =
    SupplierTO(
        id = id,
        name = name,
        contactPerson = contactPerson,
        phone = phone,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
