package com.mts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsEquipmentMaster;
import com.mts.entity.MtsEquipmentTypeMaster;

public interface MtsEquipmentMasterRepository extends JpaRepository<MtsEquipmentMaster, Long> {

	@Query(value = "select mem.* from mts_equipment_master mem, mts_equipment_type_master metm where \r\n"
			+ "mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId and metm.category = 'Asset'", nativeQuery = true)
	List<MtsEquipmentMaster> getAllAssets();

	@Query(value = "select mem.* from mts_equipment_master mem, mts_equipment_type_master metm where \r\n"
			+ "mem.mtsEquipTypeMasterId = metm.mtsEquipTypeMasterId and metm.category <> 'Asset'", nativeQuery = true)
	List<MtsEquipmentMaster> getAllConsumables();

}
