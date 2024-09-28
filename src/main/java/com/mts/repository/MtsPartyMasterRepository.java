package com.mts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsPartyMaster;

public interface MtsPartyMasterRepository extends JpaRepository<MtsPartyMaster, Long> {

//	@Query(value = "select pm.* from mts_party_master pm, company com where \r\n"
//			+ "pm.companyId = com.companyId", nativeQuery = true)
//	List<MtsPartyMaster> getAllParties();

	@Query(value = "select mpm.mtsPartyMasterId, mpm.partyName "
			+ " from mts_party_master mpm, mts_party_address mpa "
			+ " where mpm.mtsPartyMasterId = mpa.mtsPartyMasterId group by mpm.mtsPartyMasterId", nativeQuery = true)
	List<Map<String, Object>> getPartyMasterIdNameForChallan();
	
	@Query(value = "select mpm.mtsPartyMasterId, mpm.partyName "
			+ " from mts_party_master mpm group by mpm.mtsPartyMasterId", nativeQuery = true)
	List<Map<String, Object>> getPartyMasterIdNameForAddress();

	Optional<MtsPartyMaster> findByMtsPartyMasterId(Long mtsPartyMasterId);

}
