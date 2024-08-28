package com.mts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsEquipmentMaster;

public interface MtsEquipmentMasterRepository extends JpaRepository<MtsEquipmentMaster, Long> {

	@Query(value = "select mem.*, mlm.mtsLocationName from mts_equipment_master mem, mts_equipment_type_master metm,"
			+ " mts_location_master mlm where mlm.mtsLocationMasterId = mem.mtsLocationMasterId\r\n"
			+ " and mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId and metm.category = 'Asset'", nativeQuery = true)
	List<Map<String, Object>> getAllAssets();

	@Query(value = "select mem.*, mlm.mtsLocationName from mts_equipment_master mem, mts_equipment_type_master metm,"
			+ " mts_location_master mlm where mlm.mtsLocationMasterId = mem.mtsLocationMasterId and\r\n"
			+ "mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId and metm.category <> 'Asset'", nativeQuery = true)
	List<Map<String, Object>> getAllConsumables();

	Optional<MtsEquipmentMaster> findByMtsEquipMasterId(Long mtsEquipMasterId);

	MtsEquipmentMaster findByMtsEquipMasterCode(String mtsEquipMasterCode);

}
