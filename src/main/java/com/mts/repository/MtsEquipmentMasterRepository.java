package com.mts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsEquipmentMaster;

public interface MtsEquipmentMasterRepository extends JpaRepository<MtsEquipmentMaster, Long> {

	@Query(value = "SELECT mem.*, mea.totalNo, mea.inUse, mea.available, mlm.mtsLocationName, mqc.qrCodeImage\r\n"
			+ "FROM mts_equipment_master mem\r\n"
			+ "JOIN mts_equipment_type_master metm ON mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId\r\n"
			+ "JOIN mts_qr_code mqc ON mem.mtsQrId = mqc.mtsQrId\r\n"
			+ "LEFT JOIN mts_location_master mlm ON mlm.mtsLocationMasterId = mem.mtsLocationMasterId\r\n"
			+ "JOIN mts_equip_availability mea ON mem.mtsEquipMasterId = mea.mtsEquipMasterId\r\n"
			+ "WHERE metm.category = 'Asset'\r\n"
			+ "ORDER BY createDate DESC;\r\n"
			+ "", nativeQuery = true)
	List<Map<String, Object>> getAllAssets();

	@Query(value = "SELECT mem.*, mea.totalNo, mea.inUse, mea.available, mlm.mtsLocationName, mqc.qrCodeImage\r\n"
			+ "FROM mts_equipment_master mem\r\n"
			+ "JOIN mts_equipment_type_master metm ON mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId\r\n"
			+ "JOIN mts_qr_code mqc ON mem.mtsQrId = mqc.mtsQrId\r\n"
			+ "LEFT JOIN mts_location_master mlm ON mlm.mtsLocationMasterId = mem.mtsLocationMasterId\r\n"
			+ "JOIN mts_equip_availability mea ON mem.mtsEquipMasterId = mea.mtsEquipMasterId\r\n"
			+ "WHERE metm.category <> 'Asset'\r\n"
			+ "ORDER BY createDate DESC;\r\n"
			+ "", nativeQuery = true)
	List<Map<String, Object>> getAllConsumables();

	Optional<MtsEquipmentMaster> findByMtsEquipMasterId(Long mtsEquipMasterId);

	MtsEquipmentMaster findByMtsEquipMasterCode(String mtsEquipMasterCode);

	@Query(value = "select * from mts_equipment_master em, mts_location_master lm \r\n"
			+ "where lm.mtsLocationMasterId = em.mtsLocationMasterId and lm.type = :type", nativeQuery = true)
	List<MtsEquipmentMaster> getEquipmentsByLocationType(String type);
	
	@Query(value = "SELECT em.mtsEquipMasterId, em.mtsEquipMasterCode, em.mtsEquipName, em.serialNo, em.dateOfPurchase,"
			+ " em.lastDateOfWarranty, em.currentState, ced.mtsChallanId, cd.mtsChallanCode, lm.mtsLocationName, "
			+ " lm.type, pa.emailAddress, pa.contactNumber FROM mts_equipment_master em "
			+ "			LEFT JOIN mts_location_master lm ON lm.mtsLocationMasterId = em.mtsLocationMasterId "
			+ "			LEFT JOIN mts_challan_equip_dtl ced ON em.mtsEquipMasterId = ced.mtsEquipMasterId "
			+ "			LEFT JOIN mts_challan_document cd ON ced.mtsChallanId = cd.mtsChallanId "
			+ "         LEFT JOIN mts_party_address pa ON cd.consignorId = pa.mtsPartyMasterId "
			+ "			WHERE lm.type = :type", nativeQuery = true)
	List<Map<String, Object>> getEquipmentsWithChallanByLocationType(String type);

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
			+ "    ON mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId\r\n"
			+ "JOIN \r\n"
			+ "    mts_equip_availability mea \r\n"
			+ "    ON mea.mtsEquipMasterId = mem.mtsEquipMasterId\r\n"
			+ "WHERE\r\n"
			+ "    mea.available > 0", nativeQuery = true)
	List<Map<String, Object>> getTypeWiseGoodsData();

	@Query(value = "SELECT\r\n"
			+ "    em.mtsLocationMasterId AS fromLocationId,\r\n"
			+ "    lm_from.description AS fromLocation,\r\n"
			+ "    cd.despFrmLocationMasterId, cd.despToLocationMasterId,\r\n"
			+ "    lm_to.description AS toLocation\r\n"
			+ "FROM\r\n"
			+ "    mts_challan_equip_dtl ce\r\n"
			+ "JOIN \r\n"
			+ "    mts_equipment_master em ON ce.mtsEquipMasterId = em.mtsEquipMasterId\r\n"
			+ "JOIN \r\n"
			+ "    mts_location_master lm_from ON em.mtsLocationMasterId = lm_from.mtsLocationMasterId\r\n"
			+ "JOIN\r\n"
			+ "    mts_challan_document cd ON ce.mtsChallanId = cd.mtsChallanId\r\n"
			+ "LEFT JOIN\r\n"
			+ "    mts_location_master lm_to ON (lm_to.mtsLocationMasterId = 5 OR lm_to.mtsLocationMasterId = cd.despToLocationMasterId)\r\n"
			+ "WHERE\r\n"
			+ "    ce.mtsEquipMasterId = :mtsEquipMasterId"
			+ "", nativeQuery = true)
	List<Map<String, Object>> fetchLocations(Long mtsEquipMasterId);


}
