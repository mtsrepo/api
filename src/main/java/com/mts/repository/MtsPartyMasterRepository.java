package com.mts.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsPartyMaster;

public interface MtsPartyMasterRepository extends JpaRepository<MtsPartyMaster, Long> {

//	@Query(value = "select pm.* from mts_party_master pm, company com where \r\n"
//			+ "pm.companyId = com.companyId", nativeQuery = true)
//	List<MtsPartyMaster> getAllParties();

	@Query(value = "select mtsPartyMasterId, partyName, emailAddress, contactNumber "
			+ "from mts_party_master", nativeQuery = true)
	List<Map<String, Object>> getPartyMasterIdName();

}
