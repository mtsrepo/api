package com.mts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsEquipmentMaster;

public interface MtsEquipmentMasterRepository extends JpaRepository<MtsEquipmentMaster, Long> {

	@Query(value = "select mem.*, mlm.mtsLocationName,mqc.qrCodeImage from mts_equipment_master mem, mts_equipment_type_master metm,"
			+ " mts_location_master mlm, mts_qr_code mqc where mlm.mtsLocationMasterId = mem.mtsLocationMasterId\r\n"
			+ " and mem.mtsQrId = mqc.mtsQrId"
			+ " and mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId and metm.category = 'Asset' order by createDate DESC", nativeQuery = true)
	List<Map<String, Object>> getAllAssets();

	@Query(value = "select mem.*, mlm.mtsLocationName,mqc.qrCodeImage from mts_equipment_master mem, mts_equipment_type_master metm,"
			+ " mts_location_master mlm where mlm.mtsLocationMasterId = mem.mtsLocationMasterId and\r\n"
			+ " and mem.mtsQrId = mqc.mtsQrId"
			+ "mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId and metm.category <> 'Asset' order by createDate DESC", nativeQuery = true)
	List<Map<String, Object>> getAllConsumables();

	Optional<MtsEquipmentMaster> findByMtsEquipMasterId(Long mtsEquipMasterId);

	MtsEquipmentMaster findByMtsEquipMasterCode(String mtsEquipMasterCode);

	@Query(value = "select * from mts_equipment_master em, mts_location_master lm \r\n"
			+ "where lm.mtsLocationMasterId = em.mtsLocationMasterId and lm.type = :type", nativeQuery = true)
	List<MtsEquipmentMaster> getEquipmentsByLocationType(String type);
	
	@Query(value = "SELECT em.*, ced.mtsChallanId, cd.mtsChallanCode\r\n"
			+ "FROM mts_equipment_master em\r\n"
			+ "LEFT JOIN mts_location_master lm ON lm.mtsLocationMasterId = em.mtsLocationMasterId\r\n"
			+ "LEFT JOIN mts_challan_equip_dtl ced ON em.mtsEquipMasterId = ced.mtsEquipMasterId\r\n"
			+ "LEFT JOIN mts_challan_document cd ON ced.mtsChallanId = cd.mtsChallanId\r\n"
			+ "WHERE lm.type = :type", nativeQuery = true)
	List<Map<String, Object>> getEquipmentsWithChallanByLocationType(String type);

}
