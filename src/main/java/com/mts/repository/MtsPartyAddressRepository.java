package com.mts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mts.entity.MtsPartyAddress;

public interface MtsPartyAddressRepository extends JpaRepository<MtsPartyAddress, Long> {

	@Query(value = "select pa.* from mts_party_address pa, company com, mts_party_master pm where \r\n"
			+ " pa.companyId = com.companyId and pm.mtsPartyMasterId = pa.mtsPartyMasterId", nativeQuery = true)
	List<MtsPartyAddress> getAllPartyAddresses();

	@Query(value = "select pa.mtsPartyAddressId, pa.addressCode, pa.branchDetails, pa.partyAddress, pa.GSTN,"
			+ " pa.emailAddress, pa.contactNumber from mts_party_address pa,"
			+ " mts_party_master pm where pa.mtsPartyMasterId = :mtsPartyMasterId and "
			+ "pa.mtsPartyMasterId = pm.mtsPartyMasterId", nativeQuery = true)
	List<Map<String, Object>> getAddressByPartyId(@Param("mtsPartyMasterId") Long mtsPartyMasterId);

	Optional<MtsPartyAddress> findByMtsPartyAddressId(Long mtsPartyAddressId);

}
