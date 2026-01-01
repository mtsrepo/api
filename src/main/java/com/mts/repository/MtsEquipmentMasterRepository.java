package com.mts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mts.entity.MtsEquipmentMaster;

public interface MtsEquipmentMasterRepository extends JpaRepository<MtsEquipmentMaster, Long> {

	@Query(value = "SELECT mem.*, metm.name, mea.totalNo, mea.inUse, mea.available, mlm.mtsLocationName, mqc.qrCodeImage\r\n"
			+ "FROM mts_equipment_master mem\r\n"
			+ "JOIN mts_equipment_type_master metm ON mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId\r\n"
			+ "JOIN mts_qr_code mqc ON mem.mtsQrId = mqc.mtsQrId\r\n"
			+ "LEFT JOIN mts_location_master mlm ON mlm.mtsLocationMasterId = mem.mtsLocationMasterId\r\n"
			+ "JOIN mts_equip_availability mea ON mem.mtsEquipMasterId = mea.mtsEquipMasterId\r\n"
			+ "WHERE metm.category = 'Asset'\r\n"
			+ "ORDER BY createDate DESC;\r\n"
			+ "", nativeQuery = true)
	List<Map<String, Object>> getAllAssets();

	@Query(value = "SELECT \r\n"
			+ "    mem.*, \r\n"
			+ "    metm.name, \r\n"
			+ "    mea.totalNo, \r\n"
			+ "    mea.inUse, \r\n"
			+ "    mea.available, \r\n"
			+ "    mlm.mtsLocationName, \r\n"
			+ "    mqc.qrCodeImage,\r\n"
			+ "    mpa.mtsPartyMasterId,\r\n"
			+ "    mpa.mtsPartyAddressId\r\n"
			+ "FROM mts_equipment_master mem\r\n"
			+ "JOIN mts_equipment_type_master metm \r\n"
			+ "    ON mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId\r\n"
			+ "JOIN mts_qr_code mqc \r\n"
			+ "    ON mem.mtsQrId = mqc.mtsQrId\r\n"
			+ "LEFT JOIN mts_location_master mlm \r\n"
			+ "    ON mlm.mtsLocationMasterId = mem.mtsLocationMasterId\r\n"
			+ "LEFT JOIN mts_party_address mpa\r\n"
			+ "    ON mlm.mtsPartyAddressId = mpa.mtsPartyAddressId\r\n"
			+ "JOIN mts_equip_availability mea \r\n"
			+ "    ON mem.mtsEquipMasterId = mea.mtsEquipMasterId\r\n"
			+ "WHERE \r\n"
			+ "    metm.category <> 'Asset'\r\n"
			+ "ORDER BY \r\n"
			+ "    mem.createDate DESC;\r\n"
			+ "", nativeQuery = true)
	List<Map<String, Object>> getAllConsumables();

	Optional<MtsEquipmentMaster> findByMtsEquipMasterId(Long mtsEquipMasterId);

	MtsEquipmentMaster findByMtsEquipMasterCode(String mtsEquipMasterCode);

	@Query(value = "select * from mts_equipment_master em, mts_location_master lm \r\n"
			+ "where lm.mtsLocationMasterId = em.mtsLocationMasterId and lm.type = :type", nativeQuery = true)
	List<MtsEquipmentMaster> getEquipmentsByLocationType(String type);
	
	@Query(value = "SELECT DISTINCT em.mtsEquipMasterId, em.mtsEquipMasterCode, em.mtsEquipName, em.serialNo, em.dateOfPurchase, em.lastDateOfWarranty, \r\n"
			+ "em.currentStatus, cd.mtsChallanId, cd.mtsChallanCode, lm.mtsLocationName, lm.type, pa.emailAddress, pa.contactNumber\r\n"
			+ "FROM mts_equipment_master em\r\n"
			+ "LEFT JOIN mts_location_master lm \r\n"
			+ "    ON lm.mtsLocationMasterId = em.mtsLocationMasterId\r\n"
			+ "LEFT JOIN (\r\n"
			+ "    SELECT \r\n"
			+ "        mtsEquipMasterId, \r\n"
			+ "        MAX(mtsChallanId) AS latestChallanId\r\n"
			+ "    FROM mts_challan_equip_dtl\r\n"
			+ "    GROUP BY mtsEquipMasterId\r\n"
			+ ") latest \r\n"
			+ "    ON latest.mtsEquipMasterId = em.mtsEquipMasterId\r\n"
			+ "LEFT JOIN mts_challan_document cd \r\n"
			+ "    ON cd.mtsChallanId = latest.latestChallanId\r\n"
			+ "LEFT JOIN mts_party_address pa \r\n"
			+ "    ON cd.consignorId = pa.mtsPartyMasterId \r\n"
			+ "    AND pa.mtsPartyAddressId = lm.mtsPartyAddressId\r\n"
			+ "LEFT JOIN mts_status_master sm \r\n"
			+ "    ON sm.statusId = em.currentStatus\r\n"
			+ "LEFT JOIN mts_inventory_transaction mit \r\n"
			+ "    ON mit.mtsEquipMasterId = em.mtsEquipMasterId\r\n"
			+ "WHERE mit.isActive = 1\r\n"
			+ "  AND em.currentStatus = :type ", nativeQuery = true)
	List<Map<String, Object>> getEquipmentsWithChallanByLocationType(int type);

	@Query(value = "SELECT \r\n"
			+ "    mem.mtsEquipMasterId, \r\n"
			+ "    mem.mtsEquipName, \r\n"
			+ "    mem.serialNo, \r\n"
			+ "    metm.category, \r\n"
			+ "    metm.mtsEquipTypeMasterId, \r\n"
			+ "    metm.name AS mtsEquipTypeName, \r\n"
			+ "    mea.available \r\n"
			+ "FROM \r\n"
			+ "    mts_equipment_master mem\r\n"
			+ "JOIN \r\n"
			+ "    mts_equipment_type_master metm \r\n"
			+ "    ON mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId\r\n"		//, nativeQuery = true)
			+ "JOIN \r\n"															//need to recheck as want 
			+ "    mts_equip_availability mea \r\n"
			+ "    ON mea.mtsEquipMasterId = mem.mtsEquipMasterId\r\n"
			+ "WHERE\r\n"
			+ "    mea.available > 0", nativeQuery = true)
	List<Map<String, Object>> getTypeWiseGoodsData();

	@Query(value = "SELECT\r\n"
			+ "    em.mtsLocationMasterId AS fromLocationId,\r\n"
			+ "    lm_from.description AS fromLocation,\r\n"
			+ "    -- Returning both 1 and 2 for toLocationId with their descriptions\r\n"
			+ "    lm_to.mtsLocationMasterId AS toLocationId,\r\n"
			+ "    lm_to.description AS toLocationDescription\r\n"
			+ "FROM\r\n"
			+ "    mts_equipment_master em\r\n"
			+ "LEFT JOIN \r\n"
			+ "    mts_location_master lm_from ON em.mtsLocationMasterId = lm_from.mtsLocationMasterId\r\n"
			+ "LEFT JOIN\r\n"
			+ "    mts_location_master lm_to ON lm_to.mtsLocationMasterId IN (1, 2)  -- Ensures toLocation is 1 or 2\r\n"
			+ "WHERE em.mtsEquipMasterId = :mtsEquipMasterId;", nativeQuery = true)
	List<Map<String, Object>> fetchLocations(Long mtsEquipMasterId);

	@Query(value = "SELECT\r\n"
			+ "    em.mtsLocationMasterId AS fromLocationId,\r\n"
			+ "    lm_from.description AS fromLocation\r\n"
			+ "FROM\r\n"
			+ "    mts_equipment_master em\r\n"
			+ "LEFT JOIN \r\n"
			+ "    mts_location_master lm_from ON em.mtsLocationMasterId = lm_from.mtsLocationMasterId\r\n"
			+ "WHERE em.mtsEquipMasterId = :mtsEquipMasterId", nativeQuery = true)
	Map<String, Object> fetchFromLocation(Long mtsEquipMasterId);

	@Query(value = "SELECT\r\n"
			+ "    lm_to.mtsLocationMasterId AS toLocationId,\r\n"
			+ "    lm_to.description AS toLocationDescription\r\n"
			+ "FROM\r\n"
			+ "    mts_location_master lm_to\r\n"
			+ "WHERE\r\n"
			+ "    lm_to.mtsLocationMasterId IN (:mtsLocationMasterId)\r\n"		
			+ "UNION\r\n"
			+ "SELECT\r\n"
			+ "    cd.despToLocationMasterId AS toLocationId,\r\n"
			+ "    lm_linked.description AS toLocationDescription\r\n"
			+ "FROM\r\n"
			+ "    mts_challan_document cd\r\n"
			+ "LEFT JOIN\r\n"
			+ "    mts_location_master lm_linked ON lm_linked.mtsLocationMasterId = cd.despToLocationMasterId\r\n"
			+ "LEFT JOIN\r\n"
			+ "    mts_challan_equip_dtl ced ON ced.mtsChallanId = cd.mtsChallanId AND ced.mtsEquipMasterId = :mtsEquipMasterId\r\n"
			+ "WHERE ced.mtsEquipMasterId = :mtsEquipMasterId\r\n"
			+ "    AND cd.despToLocationMasterId IS NOT NULL", nativeQuery = true)
	List<Map<String, Object>> fetchToLocation(Long mtsLocationMasterId, Long mtsEquipMasterId);

	
	@Query(value = "SELECT \r\n"
			+ "			    mem.mtsEquipMasterId, mem.mtsEquipMasterCode, mem.mtsEquipName, mem.serialNo, \r\n"
			+ "			    mcd.mtsChallanId, mcd.challanName, mea.totalNo, mea.inUse, mea.available, \r\n"
			+ "			   -- mlm.mtsLocationName, \r\n"
			+ "			   -- mqc.qrCodeImage  \r\n"
			+ "                            mit.mtsChallanEquipId, mit.inTransitOrComplete\r\n"
			+ "			FROM \r\n"
			+ "			    mts_equipment_master mem\r\n"
			+ "			JOIN \r\n"
			+ "			    mts_equipment_type_master met ON mem.mtsEquipTypeMasterId = met.mtsEquipTypeMasterId\r\n"
			+ "			LEFT JOIN \r\n"
			+ "			    mts_qr_code mqc ON mem.mtsQrId = mqc.mtsQrId\r\n"
			+ "			LEFT JOIN \r\n"
			+ "			    mts_equip_availability mea ON mem.mtsEquipMasterId = mea.mtsEquipMasterId\r\n"
			+ "			LEFT JOIN \r\n"
			+ "			    mts_challan_equip_dtl mce ON mce.mtsEquipMasterId = mem.mtsEquipMasterId\r\n"
			+ "			LEFT JOIN \r\n"
			+ "			    mts_challan_document mcd ON mce.mtsChallanId = mcd.mtsChallanId\r\n"
			+ "			-- LEFT JOIN \r\n"
			+ "			  --  mts_location_master mlm ON mlm.mtsLocationMasterId IN (mcd.despFrmLocationMasterId, mcd.despToLocationMasterId)\r\n"
			+ "                        LEFT JOIN \r\n"
			+ "                            mts_inventory_transaction mit ON mem.mtsEquipMasterId = mit.mtsEquipMasterId and mce.mtsChallanEquipId = mit.mtsChallanEquipId\r\n"
			+ "			WHERE \r\n"
			+ "			    (\r\n"
			+ "			        (mem.mtsLocationMasterId IS NULL AND mem.isActive IS NULL) \r\n"
			+ "			        OR\r\n"
			+ "			        :mtsLocationMasterId in (mcd.despFrmLocationMasterId,mcd.despToLocationMasterId) and mit.isActive = 1\r\n"
			+ "					and mit.fromLocationId <> :mtsLocationMasterId  \r\n"
			+ "			           -- OR (mem.mtsLocationMasterId <> 2 AND mcd.despToLocationMasterId = 1 and mcd.despToLocationMasterId)\r\n"
			+ "			        \r\n"
			+ "			    )\r\n"
			+ "			    AND met.category = 'Asset'", nativeQuery = true)
	List<Map<String, Object>> equipmentFromLocationOfChallans(Long mtsLocationMasterId);

	
	@Query(value = "SELECT \r\n"
			+ "	mem.mtsEquipMasterId, mem.mtsEquipMasterCode, mem.mtsEquipName, mem.serialNo, \r\n"
			+ "	mcd.mtsChallanId, mcd.challanName, mea.totalNo, mea.inUse, mea.available, \r\n"
			+ "	mcd.despFrmLocationMasterId, mcd.despToLocationMasterId, mce.mtsChallanEquipId, mit.inventoryTransactionId, \r\n"
			+ "	frmLoc.mtsLocationName AS despFrmLocationName, toLoc.mtsLocationName AS despToLocationName, \r\n"
			+ "	:mtsLocationMasterId as mtsLocationMasterId, mem.mtsLocationMasterId AS currentLocationId \r\n"
			+ "FROM \r\n"
			+ "	mts_equipment_master mem\r\n"
			+ "LEFT JOIN \r\n"
			+ "	mts_equipment_type_master met ON mem.mtsEquipTypeMasterId = met.mtsEquipTypeMasterId\r\n"
			+ "LEFT JOIN \r\n"
			+ "	mts_qr_code mqc ON mem.mtsQrId = mqc.mtsQrId\r\n"
			+ "LEFT JOIN \r\n"
			+ "	mts_equip_availability mea ON mem.mtsEquipMasterId = mea.mtsEquipMasterId\r\n"
			+ "LEFT JOIN \r\n"
			+ "	mts_challan_equip_dtl mce ON mce.mtsEquipMasterId = mem.mtsEquipMasterId\r\n"
			+ "LEFT JOIN \r\n"
			+ "	mts_challan_document mcd ON mce.mtsChallanId = mcd.mtsChallanId\r\n"
			+ "LEFT JOIN \r\n"
			+ "	mts_inventory_transaction mit ON mem.mtsEquipMasterId = mit.mtsEquipMasterId and "
			+ "(mit.inTransitOrComplete is null OR mit.inTransitOrComplete <> 0) and (mit.isActive is null OR mit.isActive <> 0)"
			+ "	LEFT JOIN \r\n"
			+ "    mts_location_master frmLoc ON mcd.despFrmLocationMasterId = frmLoc.mtsLocationMasterId\r\n"
			+ "LEFT JOIN \r\n"
			+ "    mts_location_master toLoc ON mcd.despToLocationMasterId = toLoc.mtsLocationMasterId \r\n"
			+ "WHERE \r\n"
			+ "	(\r\n"
			+ "		(mem.mtsLocationMasterId IS NULL AND mem.currentStatus IS NULL) \r\n"
			+ "		or\r\n"
			+ "		(mcd.despFrmLocationMasterId = :mtsLocationMasterId and mem.currentStatus = :statusId and mcd.isActive = 1)\r\n"
			+ "		or \r\n"
			+ "		(mcd.despToLocationMasterId = :mtsLocationMasterId and mem.currentStatus = 2 and mcd.isActive = 1)\r\n"
			+ "	)\r\n"
			+ "	AND met.category = 'Asset' ", nativeQuery = true)
	List<Map<String, Object>> equipmentFromLocationAndStatus(Long mtsLocationMasterId, int statusId);
	
	
	@Query(value = "SELECT \r\n"
			+ "				mem.mtsEquipMasterId, mem.mtsEquipMasterCode, mem.mtsEquipName, mem.serialNo, \r\n"
			+ "				 mea.totalNo, mea.inUse, mea.available, mit.inventoryTransactionId, \r\n"
			+ "				 mem.mtsLocationMasterId AS currentLocationId \r\n"
			+ "			FROM \r\n"
			+ "				mts_equipment_master mem\r\n"
			+ "			LEFT JOIN \r\n"
			+ "				mts_equipment_type_master met ON mem.mtsEquipTypeMasterId = met.mtsEquipTypeMasterId\r\n"
			+ "			LEFT JOIN \r\n"
			+ "				mts_qr_code mqc ON mem.mtsQrId = mqc.mtsQrId\r\n"
			+ "			LEFT JOIN \r\n"
			+ "				mts_equip_availability mea ON mem.mtsEquipMasterId = mea.mtsEquipMasterId\r\n"
			+ "			LEFT JOIN \r\n"
			+ "				mts_inventory_transaction mit ON mem.mtsEquipMasterId = mit.mtsEquipMasterId and \r\n"
			+ "			(mit.inTransitOrComplete is null OR mit.inTransitOrComplete <> 0) and (mit.isActive is null OR mit.isActive <> 0)\r\n"
			+ "				\r\n"
			+ "			WHERE \r\n"
			+ "				mem.mtsLocationMasterId IS NULL OR mem.currentStatus IS NULL\r\n"
			+ "				AND met.category = 'Asset' ", nativeQuery = true)
	List<Map<String, Object>> nonLocatedEquipmentsFromStatus();

	Optional<MtsEquipmentMaster> findBySerialNo(String serialNo);

	@Query(value = "SELECT DISTINCT\r\n"
			+ "    mem.mtsEquipMasterId,\r\n"
			+ "    mem.mtsEquipName,\r\n"
			+ "    mem.serialNo,\r\n"
			+ "    metm.category,\r\n"
			+ "    metm.mtsEquipTypeMasterId,\r\n"
			+ "    metm.name AS mtsEquipTypeName,\r\n"
			+ "    mea.available,\r\n"
			+ "    mea.inUse\r\n"
			+ "FROM mts_equipment_master mem\r\n"
			+ "JOIN mts_equipment_type_master metm\r\n"
			+ "    ON mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId\r\n"
			+ "LEFT JOIN mts_equip_availability mea\r\n"
			+ "    ON mea.mtsEquipMasterId = mem.mtsEquipMasterId\r\n"
			+ "LEFT JOIN mts_location_master lm\r\n"
			+ "    ON lm.mtsLocationMasterId = mem.mtsLocationMasterId\r\n"
			+ "LEFT JOIN mts_inventory_transaction mit\r\n"
			+ "    ON mit.mtsEquipMasterId = mem.mtsEquipMasterId\r\n"
			+ "    AND mit.isActive = 1\r\n"
			+ "    AND mit.inTransitOrComplete = 1\r\n"
			+ "WHERE\r\n"
			+ "    mem.isActive = 1\r\n"
			+ "    AND mit.inventoryTransactionId IS NULL\r\n"
			+ "    AND (\r\n"
			+ "        -- ASSETS\r\n"
			+ "        (\r\n"
			+ "            metm.category = 'Asset'\r\n"
			+ "            AND mem.mtsLocationMasterId = :mtsLocationMasterId\r\n"
			+ "            AND mem.currentStatus <> 2\r\n"
			+ "            AND (\r\n"
			+ "                (lm.type = 1 AND mea.available > 0)\r\n"
			+ "                OR\r\n"
			+ "                (lm.type = 3 AND mea.inUse > 0)\r\n"
			+ "            )\r\n"
			+ "        )\r\n"
			+ "        OR\r\n"
			+ "        -- CONSUMABLES\r\n"
			+ "        (\r\n"
			+ "            metm.category <> 'Asset'\r\n"
			+ "        )\r\n"
			+ "    )", nativeQuery = true)
	List<Map<String, Object>> getTypeWiseGoodsData(Long mtsLocationMasterId);

	@Query(value = "SELECT em.*\r\n"
			+ "FROM mts_equipment_master em\r\n"
			+ "LEFT JOIN mts_inventory_transaction it\r\n"
			+ "    ON it.mtsEquipMasterId = em.mtsEquipMasterId\r\n"
			+ "    AND it.inTransitOrComplete = 1\r\n"
			+ "    AND it.isActive = 1\r\n"
			+ "WHERE\r\n"
			+ "    em.mtsLocationMasterId = :locationId\r\n"
			+ "    AND em.currentStatus != 2\r\n"
			+ "    AND it.inventoryTransactionId IS NULL;\r\n"
			+ "", nativeQuery = true)
		List<Map<String, Object>> getDispatchableEquipment(
		    @Param("locationId") Long locationId
		);


	@Query(value = "SELECT \r\n"
			+ "		        mem.mtsEquipMasterId,\r\n"
			+ "		        mem.mtsEquipMasterCode,\r\n"
			+ "		        mem.mtsEquipName,\r\n"
			+ "		        mem.serialNo,\r\n"
			+ "		        mit.inventoryTransactionId,\r\n"
			+ "		        mit.fromLocationId\r\n"
			+ "		    FROM mts_equipment_master mem\r\n"
			+ "		    JOIN mts_inventory_transaction mit\r\n"
			+ "		        ON mit.mtsEquipMasterId = mem.mtsEquipMasterId\r\n"
			+ "		    WHERE mem.currentStatus = 2\r\n"
			+ "		      AND mit.inTransitOrComplete = 1\r\n"
			+ "		      AND mit.toLocationId = :locationId\r\n"
			+ "		      AND mit.isActive = 1", nativeQuery = true)
		List<Map<String, Object>> getReceivableEquipment(
		    @Param("locationId") Long locationId
		);

	@Query(value = "SELECT em.*\r\n"
			+ "FROM mts_equipment_master em\r\n"
			+ "LEFT JOIN mts_inventory_transaction it\r\n"
			+ "    ON it.mtsEquipMasterId = em.mtsEquipMasterId\r\n"
			+ "    AND it.inTransitOrComplete = 1\r\n"
			+ "    AND it.isActive = 1\r\n"
			+ "WHERE\r\n"
			+ "    em.mtsLocationMasterId IS NULL\r\n"
			+ "    AND em.currentStatus IS NULL\r\n"
			+ "    AND it.inventoryTransactionId IS NULL;\r\n"
			+ "", nativeQuery = true)
	List<Map<String, Object>> findExternalDispatchable();




}
