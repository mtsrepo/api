package com.mts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mts.entity.MtsPartyMaster;

public interface MtsPartyMasterRepository extends JpaRepository<MtsPartyMaster, Long> {

	@Query(value = "select pm.* from mts_party_master pm, company com where \r\n"
			+ "pm.companyId = com.companyId", nativeQuery = true)
	List<MtsPartyMaster> getAllParties();

}
