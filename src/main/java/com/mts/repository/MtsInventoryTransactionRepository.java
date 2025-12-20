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
	
	@Query(value = "SELECT *\n"
			+ "		    FROM mts_inventory_transaction\n"
			+ "		    WHERE mtsEquipMasterId = :equipId\n"
			+ "		      AND inTransitOrComplete = 1\n"
			+ "		    ORDER BY createdOn DESC\n"
			+ "		    LIMIT 1", nativeQuery = true)
		MtsInventoryTransaction findOpenTransaction(@Param("equipId") Long equipId);


}
