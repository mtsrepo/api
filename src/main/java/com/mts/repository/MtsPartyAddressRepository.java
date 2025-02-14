package com.mts.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mts.entity.MtsPartyAddress;

public interface MtsPartyAddressRepository extends JpaRepository<MtsPartyAddress, Long> {

	@Query(value = "select pm.partyName, pa.* from mts_party_address pa, company com, mts_party_master pm where \r\n"
			+ " pa.companyId = com.companyId and pm.mtsPartyMasterId = pa.mtsPartyMasterId", nativeQuery = true)
	List<Map<String, Object>> getAllPartyAddresses();

	@Query(value = "select pa.mtsPartyAddressId, pa.contactPerson, pa.designation, pa.addressCode, pa.addressLine1,"
			+ " pa.addressLine2, pa.locationAddressDesc, pa.GSTN, pa.emailAddress, pa.department, pa.pincode, pa.contactNumber from mts_party_address pa,"
			+ " mts_party_master pm where pa.mtsPartyMasterId = :mtsPartyMasterId and "
			+ "pa.mtsPartyMasterId = pm.mtsPartyMasterId", nativeQuery = true)
//	@Query(value = "select lm.mtsLocationMasterId, pa.contactPerson, pa.designation, pa.addressCode, pa.addressLine1,\r\n"
//			+ " pa.addressLine2, lm.description, pa.GSTN, pa.emailAddress, pa.department, pa.pincode, pa.contactNumber from mts_party_address pa,\r\n"
//			+ " mts_party_master pm, mts_location_master lm where pa.mtsPartyMasterId = :mtsPartyMasterId and \r\n"
//			+ " pa.mtsPartyMasterId = pm.mtsPartyMasterId and lm.mtsPartyAddressId = pa.mtsPartyAddressId", nativeQuery = true)
	List<Map<String, Object>> getAddressByPartyId(@Param("mtsPartyMasterId") Long mtsPartyMasterId);

	Optional<MtsPartyAddress> findByMtsPartyAddressId(Long mtsPartyAddressId);

}
