package org.bruwave.abacusflow.usecase.product.service.impl

import org.bruwave.abacusflow.db.product.ProductInstanceRepository
import org.bruwave.abacusflow.db.transaction.PurchaseOrderRepository
import org.bruwave.abacusflow.db.transaction.SaleOrderRepository
import org.bruwave.abacusflow.usecase.product.BasicProductInstanceTO
import org.bruwave.abacusflow.usecase.product.mapper.toBasicTO
import org.bruwave.abacusflow.usecase.product.service.ProductInstanceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProductInstanceServiceImpl(
    private val productInstanceRepository: ProductInstanceRepository,
    private val purchaseOrderRepository: PurchaseOrderRepository,
    private val saleOrderRepository: SaleOrderRepository,
) : ProductInstanceService {
    override fun listProductInstances(): List<BasicProductInstanceTO> {
        val instances = productInstanceRepository.findAll()

        val purchaseOrderIds = instances.mapNotNull { it.purchaseOrderId }.toSet()
        val saleOrderIds = instances.mapNotNull { it.saleOrderId }.toSet()

        val purchaseOrderMap = purchaseOrderRepository.findAllById(purchaseOrderIds).associateBy { it.id }
        val saleOrderMap = saleOrderRepository.findAllById(saleOrderIds).associateBy { it.id }

        return instances.map { instance ->
            val purchaseOrderNo = purchaseOrderMap.getValue(instance.purchaseOrderId).no
            val saleOrderNo =
                instance.saleOrderId?.let {
                    saleOrderMap.getValue(it).no
                }
            instance.toBasicTO(purchaseOrderNo, saleOrderNo)
        }
    }
}
