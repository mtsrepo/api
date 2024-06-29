package com.mts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mts.entity.MtsEquipmentTypeMaster;

public interface MtsEquipmentTypeMasterRepository extends JpaRepository<MtsEquipmentTypeMaster, Long> {

	List<MtsEquipmentTypeMaster> findByCategory(String category);

}
