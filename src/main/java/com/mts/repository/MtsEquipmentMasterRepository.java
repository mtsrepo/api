package com.mts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsEquipmentMaster;

public interface MtsEquipmentMasterRepository extends JpaRepository<MtsEquipmentMaster, Long> {

	@Query(value = "select mem.*, mea.totalNo, mea.inUse, mea.available, mlm.mtsLocationName, mqc.qrCodeImage"
			+ " from mts_equipment_master mem, mts_equipment_type_master metm, mts_location_master mlm, "
			+ "mts_qr_code mqc, mts_equip_availability mea where mlm.mtsLocationMasterId = mem.mtsLocationMasterId"
			+ " and mem.mtsQrId = mqc.mtsQrId and mem.mtsEquipMasterId = mea.mtsEquipMasterId"
			+ " and mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId and metm.category = 'Asset' order by createDate DESC", nativeQuery = true)
	List<Map<String, Object>> getAllAssets();

	@Query(value = "select mem.*, mea.totalNo, mea.inUse, mea.available, mlm.mtsLocationName, mqc.qrCodeImage"
			+ " from mts_equipment_master mem, mts_equipment_type_master metm, mts_location_master mlm, "
			+ "mts_qr_code mqc, mts_equip_availability mea where mlm.mtsLocationMasterId = mem.mtsLocationMasterId"
			+ " and mem.mtsQrId = mqc.mtsQrId and mem.mtsEquipMasterId = mea.mtsEquipMasterId"
			+ " and mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId and metm.category <> 'Asset' order by createDate DESC", nativeQuery = true)
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

}
