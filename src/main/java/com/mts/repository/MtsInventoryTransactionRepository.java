package com.mts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mts.entity.MtsInventoryTransaction;

@Repository
public interface MtsInventoryTransactionRepository extends JpaRepository<MtsInventoryTransaction, Long> {

	MtsInventoryTransaction findByInventoryTransactionId(Long inventoryTransactionId);

	MtsInventoryTransaction findByInventoryTransactionIdAndIsActive(Long inventoryTransactionId, int i);
	
	@Query(value = """
		    SELECT *
		    FROM mts_inventory_transaction
		    WHERE mtsEquipMasterId = :equipId
		      AND inTransitOrComplete = 1
		    ORDER BY createdOn DESC
		    LIMIT 1
		""", nativeQuery = true)
		MtsInventoryTransaction findOpenTransaction(@Param("equipId") Long equipId);


}
