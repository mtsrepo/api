package com.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mts.entity.MtsInventoryTransaction;

@Repository
public interface MtsInventoryTransactionRepository extends JpaRepository<MtsInventoryTransaction, Long> {

	MtsInventoryTransaction findByInventoryTransactionId(Long inventoryTransactionId);

	MtsInventoryTransaction findByInventoryTransactionIdAndIsActive(Long inventoryTransactionId, int i);

}
