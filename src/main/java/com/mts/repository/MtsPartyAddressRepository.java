package com.mts.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsPartyAddress;

public interface MtsPartyAddressRepository extends JpaRepository<MtsPartyAddress, Long> {

	@Query(value = "select pa.* from mts_party_address pa, company com, mts_party_master pm where \r\n"
			+ " pa.companyId = com.companyId and pm.mtsPartyMasterId = pa.mtsPartyMasterId", nativeQuery = true)
	List<MtsPartyAddress> getAllPartyAddresses();

	@Query(value = "select pm.mtsPartyMasterId, pm.name, com.companyId from mts_party_master pm, company com"
			+ " where com.companyId = pm.companyId", nativeQuery = true)
	List<Map<String, Object>> getPartyTypeIdName();

}
