package com.mts.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsEquipmentTypeMaster;

public interface MtsEquipmentTypeMasterRepository extends JpaRepository<MtsEquipmentTypeMaster, Long> {

	@Query(value = "select mtsEquipTypeMasterId, name from mts_equipment_type_master "
			+ " where category = 'Asset'", nativeQuery = true)
	List<Map<String, Object>> getAssetTypeIdName();

	@Query(value = "select mtsEquipTypeMasterId, name from mts_equipment_type_master "
			+ " where category <> 'Asset'", nativeQuery = true)
	List<Map<String, Object>> getConsumableTypeIdName();

	MtsEquipmentTypeMaster findByMtsEquipTypeMasterId(Long mtsEquipTypeMasterId);
}
