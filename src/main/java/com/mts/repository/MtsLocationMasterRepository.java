package com.mts.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsLocationMaster;

public interface MtsLocationMasterRepository extends JpaRepository<MtsLocationMaster, Long> {

	@Query(value = "select mtsLocationMasterId, mtsLocationName, type "
			+ "from mts_location_master", nativeQuery = true)
	List<Map<String, Object>> getLocationMasterIdName();

	MtsLocationMaster findByMtsLocationMasterId(Long mtsLocationMasterId);

	@Query(value="select distinct type from mts_location_master",nativeQuery = true)
	List<String> findDistinctTypes();

	MtsLocationMaster findByMtsPartyAddressId(Long despFrmLocationMasterId);

}
